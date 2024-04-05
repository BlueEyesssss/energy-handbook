package com.khaphp.energyhandbook.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class FileStore {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket_name}")
    private String bucketName;

    public void upload(String path,
                       Optional<Map<String, String>> optionalMetaData,
                       InputStream inputStream) {       //inputstream này là img của ta đã đc chuyển thành stream
        ObjectMetadata objectMetadata = new ObjectMetadata();
        optionalMetaData.ifPresent(map -> {     //này là viê rút gọn map từ optionalMetaData vào ObjectMetadata của aws s3
            if (!map.isEmpty()) {
                map.forEach(objectMetadata::addUserMetadata);
            }
        });
        String contentLengthInputStream = optionalMetaData.map(map -> map.get("Content-Length")).orElse("0");   //để tránh warn: No content length specified for stream data. Stream contents will be buffered in memory and could result in out of memory errors.
        objectMetadata.setContentLength(Long.parseLong(contentLengthInputStream));

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead);  //tạo PutObjectRequest đề setting cho img trc khi lên
                                                                                                                    //khúc cannedAcll giúp ta up img lên mà và url từ img đó sẽ đc pulbish cho mn cùng xem
                                                                                                                    //lưu path thì khi xóa cũng lấy path này ra (cùng vs xác định tên Bucket) để xác định IMG để xóa nha ra khỏi bucket nha
            amazonS3.putObject(putObjectRequest);   //đưa ảnh lên s3
            System.out.println("-> url: " + amazonS3.getUrl(bucketName, path));
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }

    public String deleteImage(String imagePath) {
        try {
            amazonS3.deleteObject(bucketName, imagePath);       //delete img khá đơn giản, xác định bucket + path tính từ bucket vào tới img cần xóa
                                                                // vd: bucket: kphpbucket, path: 808930ba-f2e7-46b2-9110-451ce7b0a28a_face1.jpg (nhớ là tính từ bucket nha)
                                                                // ko phải path như này: https://kphpbucket.s3.ap-southeast-1.amazonaws.com/808930ba-f2e7-46b2-9110-451ce7b0a28a_face1.jpg (sai vì nó ko cần phần URL https://kphpbucket.s3.ap-southeast-1.amazonaws.com/, vì ta đã xác định bucket rồi nên phần URl đầu thừa)
            return "Image deleted successfully.";
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to delete the image", e);
        }
    }
}