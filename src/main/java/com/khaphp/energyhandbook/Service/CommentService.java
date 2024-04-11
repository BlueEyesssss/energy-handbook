package com.khaphp.energyhandbook.Service;

import com.khaphp.energyhandbook.Dto.Comment.CommentDTOcreate;
import com.khaphp.energyhandbook.Dto.ResponseObject;

public interface CommentService {

    ResponseObject<Object> getAll(int pageSize, int pageIndex, String cookingRecipeId);
    ResponseObject<Object> getChildComment(String id, int pageSize, int pageIndex);
    ResponseObject<Object> create(CommentDTOcreate object);
//    ResponseObject<Object> update(CommentDTOupdate object);
    ResponseObject<Object> delete(String id);
}
