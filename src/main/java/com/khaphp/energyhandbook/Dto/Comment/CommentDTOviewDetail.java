package com.khaphp.energyhandbook.Dto.Comment;

import com.khaphp.energyhandbook.Dto.CookingRecipe.CookingRecipeDTOviewInOrtherEntity;
import com.khaphp.energyhandbook.Dto.Usersystem.UserSystemDTOviewInOrtherEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDTOviewDetail {
    private int ortherChildCmtSize;
    private List<CommentChild> commentChildrens;
}
