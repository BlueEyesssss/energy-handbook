package com.khaphp.energyhandbook.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

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
    @JsonIgnore
    private CookingRecipe cookingRecipe;

    @ManyToOne
    @JsonIgnore
    private UserSystem customer;
}
