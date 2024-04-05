package com.khaphp.energyhandbook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {
    @Id
    @UuidGenerator
    @Column(columnDefinition = "VARCHAR(36)")
    private String id;
    private Date createDate;
    private String body;
    @Column(columnDefinition = "VARCHAR(36)")
    private String parentCommentId;

    @ManyToOne
    private CookingRecipe cookingRecipe;

    @ManyToOne
    private UserSystem customer;
}
