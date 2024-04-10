package com.khaphp.energyhandbook.Dto.Comment;

import com.khaphp.energyhandbook.Dto.CookingRecipe.CookingRecipeDTOviewInOrtherEntity;
import com.khaphp.energyhandbook.Dto.Usersystem.UserSystemDTOviewInOrtherEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDTOviewAll {
    private String id;
    private Date createDate;
    private String body;
    private String parentCommentId;
    private int childCmtSize;
    private UserSystemDTOviewInOrtherEntity ownerV;
    private CookingRecipeDTOviewInOrtherEntity cookingRecipeV;
}
