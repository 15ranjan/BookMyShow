package com.example.bookmyshow.dtos;

import com.example.bookmyshow.models.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDto {

    private ResponseStatus repsonseStatus;
    private Long userId;
}
