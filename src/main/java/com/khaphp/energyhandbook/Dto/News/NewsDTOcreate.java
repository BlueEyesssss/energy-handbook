package com.khaphp.energyhandbook.Dto.News;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewsDTOcreate {
    private String employeeId;
    @Size(max=255, message = "title max length is 255")
    private String title;
    @Size(max=255, message = "body max length is 255")
    private String body;
//    private MultipartFile multipartFile;
}
