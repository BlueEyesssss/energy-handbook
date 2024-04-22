package com.khaphp.energyhandbook.Service.Impl;

import com.khaphp.energyhandbook.Constant.TypeInteract;
import com.khaphp.energyhandbook.Dto.Comment.CommentChild;
import com.khaphp.energyhandbook.Dto.Comment.CommentDTOcreate;
import com.khaphp.energyhandbook.Dto.Comment.CommentDTOviewAll;
import com.khaphp.energyhandbook.Dto.Comment.CommentDTOviewDetail;
import com.khaphp.energyhandbook.Dto.CookingRecipe.CookingRecipeDTOviewInOrtherEntity;
import com.khaphp.energyhandbook.Dto.Notification.NotificationDTOcreate;
import com.khaphp.energyhandbook.Dto.ResponseObject;
import com.khaphp.energyhandbook.Dto.Usersystem.UserSystemDTOviewInOrtherEntity;
import com.khaphp.energyhandbook.Entity.Comment;
import com.khaphp.energyhandbook.Entity.CookingRecipe;
import com.khaphp.energyhandbook.Entity.UserSystem;
import com.khaphp.energyhandbook.Repository.CommentRepository;
import com.khaphp.energyhandbook.Repository.CookingRecipeRepository;
import com.khaphp.energyhandbook.Service.CommentService;
import com.khaphp.energyhandbook.Service.NotificationService;
import com.khaphp.energyhandbook.Service.UserSystemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserSystemService userSystemService;

    @Autowired
    private CookingRecipeRepository cookingRecipeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${aws.s3.link_bucket}")
    private String linkBucket;

    @Autowired
    private NotificationService notificationService;
    @Override
    public ResponseObject<Object> getAll(int pageSize, int pageIndex, String cookingRecipeId) {
        Page<Comment> objListPage = null;
        List<Comment> objList = null;
        int totalPage = 0;
        //paging
        if(pageSize > 0 && pageIndex > 0){
            if(!cookingRecipeId.equals("")){
                objListPage = commentRepository.findAllCommentByCookingRecipeId(cookingRecipeId, PageRequest.of(pageIndex - 1, pageSize));
            }else{
                objListPage = commentRepository.findAllComment(PageRequest.of(pageIndex - 1, pageSize));
            }
            if(objListPage != null){
                totalPage = objListPage.getTotalPages();
                objList = objListPage.getContent();
            }
        }else{ //get all
            objList = commentRepository.findAll();
            pageIndex = 1;
        }
        List<CommentDTOviewAll> objListDTO = new ArrayList<>();
        objList.forEach(object -> {
            CommentDTOviewAll objectView = modelMapper.map(object, CommentDTOviewAll.class);
            objectView.setChildCmtSize(commentRepository.countAllByCookingRecipeIdAndParentCommentId(object.getCookingRecipe().getId(), object.getId()));
            objectView.setOwnerV(UserSystemDTOviewInOrtherEntity.builder()
                    .id(object.getOwner().getId())
                    .name(object.getOwner().getName())
                    .imgUrl(linkBucket + object.getOwner().getImgUrl()).build());
            objectView.setCookingRecipeV(CookingRecipeDTOviewInOrtherEntity.builder()
                    .id(object.getCookingRecipe().getId())
                    .name(object.getCookingRecipe().getName())
                    .build());
            objListDTO.add(objectView);
        });
        return ResponseObject.builder()
                .code(200).message("Success")
                .pageSize(objListDTO.size()).pageIndex(pageIndex).totalPage(totalPage)
                .data(objListDTO)
                .build();
    }

    @Override
    public ResponseObject<Object> getChildComment(String id, int pageSize, int pageIndex) {
        try{
            Comment object = commentRepository.findById(id).orElse(null);
            if(object == null) {
                throw new Exception("object not found");
            }
            CommentDTOviewDetail objectView = modelMapper.map(object, CommentDTOviewDetail.class);

            Page<Comment> listChildPage = commentRepository.findAllByCookingRecipeIdAndParentCommentIdOrderByCreateDate(object.getCookingRecipe().getId(), object.getId(), PageRequest.of(0, pageSize * pageIndex));
            int pagesize = 0;
            if(listChildPage.getTotalElements() > 0){
                List<Comment> listChild = listChildPage.getContent();
                pagesize = listChild.size();
                //set orther child cmt còn lại
                if(listChildPage.getTotalElements() == listChild.size()){
                    objectView.setOrtherChildCmtSize(0);
                }else if(listChildPage.getTotalElements() > listChild.size()){
                    objectView.setOrtherChildCmtSize((int) (listChildPage.getTotalElements() - listChild.size()));
                }

                //map data
                objectView.setCommentChildrens(new ArrayList<>());
                listChild.forEach(x -> {
                    CommentChild objectChild = modelMapper.map(x, CommentChild.class);
                    objectChild.setCookingRecipeV(CookingRecipeDTOviewInOrtherEntity.builder()
                            .id(x.getCookingRecipe().getId())
                            .name(x.getCookingRecipe().getName())
                            .build());
                    objectChild.setOwnerV(UserSystemDTOviewInOrtherEntity.builder()
                            .id(x.getOwner().getId())
                            .name(x.getOwner().getName())
                            .imgUrl(linkBucket + x.getOwner().getImgUrl())
                            .build());
                    objectChild.setReplyTo(UserSystemDTOviewInOrtherEntity.builder()
                            .id(x.getReplyTo().getId())
                            .name(x.getReplyTo().getName())
                            .imgUrl(linkBucket + x.getReplyTo().getImgUrl())
                            .build());
                    objectView.getCommentChildrens().add(objectChild);
                });
            }
            return ResponseObject.builder()
                    .code(200).message("Found")
                    .data(objectView)
                    .pageSize(pagesize)
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400).message("Exception: "+ e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseObject<Object> create(CommentDTOcreate object) {
        try{
            UserSystem owner = (UserSystem) userSystemService.getDetail(object.getOwnerId()).getData();
            if(owner == null){
                throw new Exception("owner not found");
            }
            owner.setImgUrl(owner.getImgUrl().substring(linkBucket.length()));

            UserSystem replyTo = (UserSystem) userSystemService.getDetail(object.getReplyToId()).getData();
            if(replyTo == null){
                throw new Exception("replyTo user not found");
            }
            replyTo.setImgUrl(replyTo.getImgUrl().substring(linkBucket.length()));

            CookingRecipe cookingRecipe = cookingRecipeRepository.findById(object.getCookingRecipeId()).orElse(null);
            if(cookingRecipe == null){
                throw new Exception("cookingRecipe not found");
            }
            cookingRecipe.setProductImg(cookingRecipe.getProductImg());

            Comment comment = modelMapper.map(object, Comment.class);
            comment.setOwner(owner);
            comment.setReplyTo(replyTo);
            comment.setCookingRecipe(cookingRecipe);
            comment.setCreateDate(new Date(System.currentTimeMillis()));
            if(comment.getParentCommentId() == null){
                comment.setParentCommentId("");
            }
            commentRepository.save(comment);

            //noti if customer comment answer owner of cooking recipe
            if(cookingRecipe.getCustomer().getId().equals(replyTo.getId())){
                ResponseObject rs = notificationService.create(NotificationDTOcreate.builder()
                        .userId(cookingRecipe.getCustomer().getId())
                        .title(owner.getName() +" đã bình luận vào công thức " + cookingRecipe.getName())
                        .build());
                if(rs.getCode() != 200){
                    throw new Exception(rs.getMessage());
                }
            }
            return ResponseObject.builder()
                    .code(200)
                    .message("Success")
                    .data(comment)
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: " + e.getMessage())
                    .build();
        }
    }

//    @Override
//    public ResponseObject<Object> update(CommentDTOupdate object) {
//        return null;
//    }

    @Override
    public ResponseObject<Object> delete(String id) {
        try{
            Comment object = commentRepository.findById(id).orElse(null);
            if(object == null) {
                throw new Exception("object not found");
            }
            if(commentRepository.countAllByCookingRecipeIdAndParentCommentId(object.getCookingRecipe().getId(), object.getId()) > 0){
                object.setBody("[ Bình luận này đã bị xóa ]");
                commentRepository.save(object);
            }else{
                commentRepository.delete(object);
            }
            return ResponseObject.builder()
                    .code(200)
                    .message("Success")
                    .build();
        }catch (Exception e){
            return ResponseObject.builder()
                    .code(400)
                    .message("Exception: " + e.getMessage())
                    .build();
        }
    }
}
