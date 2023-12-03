package com.example.bookmyshow.dtos;

import com.example.bookmyshow.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookMovieRequestDto {

    private Long userId;
    private Long showId;

    private List<Long> showSeatId;
}
