package com.example.bookmyshow.controllers;

import com.example.bookmyshow.dtos.BookMovieRequestDto;
import com.example.bookmyshow.dtos.BookMovieResponseDto;
import com.example.bookmyshow.models.Booking;
import com.example.bookmyshow.models.ResponseStatus;
import com.example.bookmyshow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller // giving command to spring that i would need this object in future
public class BookingController {

    //Dependency injections -->> Spring is a Dependencies Injection framework.
    // In order to use the method of SERVICES we need to create an object of BookingService
    private BookingService bookingService;

    //private BookingService bookingService = new BookingService(); Cannot use this directly as this would violate
    // dependency inversion design principles is getting violated. That's why we should inject the dependency by
    // using @Autowired annotations.
    @Autowired
    BookingController(BookingService bookingService){

        this.bookingService = bookingService;
    }
    BookMovieResponseDto  bookMovie(BookMovieRequestDto requestDto){
        //DTO - Data transfer objects
        BookMovieResponseDto responseDto = new BookMovieResponseDto();
        try{
            Booking booking = bookingService.bookMovie(
                    requestDto.getUserId(),
                    requestDto.getShowId(),
                    requestDto.getShowSeatId()
            );
            responseDto.setBookingId(booking.getId());
            responseDto.setReponseStatus(ResponseStatus.SUCCESS);
            responseDto.setAmount(booking.getPrice());
        }catch (Exception e){
            responseDto.setReponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
