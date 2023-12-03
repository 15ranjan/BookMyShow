package com.example.bookmyshow.dtos;

import com.example.bookmyshow.models.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMovieResponseDto {

    private int amount;
    private Long bookingId;

    private ResponseStatus reponseStatus;


}
