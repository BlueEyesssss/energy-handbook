package com.khaphp.energyhandbook.Dto.Notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khaphp.energyhandbook.Entity.UserSystem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationDTOcreate {
    private String userId;
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    private String title;
    @Size(min = 3, max = 255, message = "description must be between 3 and 255 characters")
    private String description;
}
