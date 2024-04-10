package com.khaphp.energyhandbook.Dto.Comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaphp.energyhandbook.Entity.CookingRecipe;
import com.khaphp.energyhandbook.Entity.UserSystem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDTOcreate {
    private String cookingRecipeId;
    private String ownerId;
    private String replyToId;
    private String body;
    private String parentCommentId;
}
