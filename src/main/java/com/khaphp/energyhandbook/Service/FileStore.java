package com.khaphp.energyhandbook.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
@Slf4j
public class FileStore {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket_name}")
    private String bucketName;

    public String uploadImg(MultipartFile file){
        String pathFileName = String.format("%s", UUID.randomUUID() + "_"+ file.getOriginalFilename());     //format: [UUID]_[name_img_from_local]
        //vd: 808930ba-f2e7-46b2-9110-451ce7b0a28a_face1.jpg --> nó lưu thằng trong folder buket của mình luôn
        //nếu format: A/[UUID]_[name_img_from_local] hay A/808930ba-f2e7-46b2-9110-451ce7b0a28a_face1.jpg
        // --> thì nó sẽ tạo thếm folder mới tên là A trong folder Bucket của mình và luu ảnh trong folder A đó
        // dựa vào đây có thể tạo nhiều folder cho từng user để có thể dễ quản lý img, file của họ
        try{
            //check if the file is empty
            if (file.isEmpty()) {
                throw new IllegalStateException("Cannot upload empty file");
            }
            //check size <10Mb, 1MB = 1 048 576 bytes
            if(file.getSize() >= 10 * 1048576){
                throw new IllegalStateException("IMG size must smaller than 10MB");
            }
            //Check if the file is an image
            if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                    IMAGE_BMP.getMimeType(),
                    IMAGE_GIF.getMimeType(),
                    IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
                throw new IllegalStateException("FIle uploaded is not an image");
            }
            //get file metadata
            Map<String, String> metadata = new HashMap<>();
            metadata.put("Content-Type", file.getContentType());
            metadata.put("Content-Length", String.valueOf(file.getSize()));

            try {
                ObjectMetadata objectMetadata = new ObjectMetadata();
                Optional.of(metadata).ifPresent(map -> {     //này là viê rút gọn map từ optionalMetaData vào ObjectMetadata của aws s3
                    if (!map.isEmpty()) {
                        map.forEach(objectMetadata::addUserMetadata);
                    }
                });
                String contentLengthInputStream = Optional.of(metadata).map(map -> map.get("Content-Length")).orElse("0");   //để tránh warn: No content length specified for stream data. Stream contents will be buffered in memory and could result in out of memory errors.
                objectMetadata.setContentLength(Long.parseLong(contentLengthInputStream));
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, pathFileName, file.getInputStream(), objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead);  //tạo PutObjectRequest đề setting cho img trc khi lên
                                                                                                                                        //khúc cannedAcll giúp ta up img lên mà và url từ img đó sẽ đc pulbish cho mn cùng xem
                                                                                                                                        //lưu path thì khi xóa cũng lấy path này ra (cùng vs xác định tên Bucket) để xác định IMG để xóa nha ra khỏi bucket nha
                amazonS3.putObject(putObjectRequest);   //đưa ảnh lên s3
                log.info("-> url: " + amazonS3.getUrl(bucketName, pathFileName));
            } catch (IOException e) {
                throw new IllegalStateException("Failed to upload file: " +e.getMessage());
            }
            return pathFileName;
        }catch (Exception e){
            deleteImage(pathFileName);
            throw new IllegalStateException("Failed to upload file: " +e.getMessage());
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