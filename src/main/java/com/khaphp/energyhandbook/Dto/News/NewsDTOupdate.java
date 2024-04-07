package com.khaphp.energyhandbook.Dto.News;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewsDTOupdate {
    private String id;
    @Size(max=255, message = "title max length is 255")
    private String title;
    @Size(max=255, message = "body max length is 255")
    private String body;
}
