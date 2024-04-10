package com.khaphp.energyhandbook.Repository;

import com.khaphp.energyhandbook.Entity.Comment;
import com.khaphp.energyhandbook.Entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    Page<Comment> findAllByCookingRecipeId(String cookingRecipeId, Pageable pageable);

    int countAllByCookingRecipeIdAndParentCommentId(String cookingRecipeId, String parentCommentId);

    Page<Comment> findAllByCookingRecipeIdAndParentCommentIdOrderByCreateDate(String cookingRecipeId, String parentCommentId, Pageable pageable);

    @Query("select c from Comment c where c.cookingRecipe.id = ?1 and c.parentCommentId = ''")
    Page<Comment> findAllCommentByCookingRecipeId(String cookingRecipeId, PageRequest of);

    @Query("select c from Comment c where c.parentCommentId = ''")
    Page<Comment> findAllComment(PageRequest of);
}
