package com.khaphp.energyhandbook.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponseObject<T> {
    private int code;
    private String message;
    private T data;
}
