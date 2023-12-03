package com.example.bookmyshow.controllers;

import com.example.bookmyshow.dtos.SignUpRequestDto;
import com.example.bookmyshow.dtos.SignUpResponseDto;
import com.example.bookmyshow.models.ResponseStatus;
import com.example.bookmyshow.models.User;
import com.example.bookmyshow.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }

    public SignUpResponseDto signUp(SignUpRequestDto requestDto){
        User user;
        SignUpResponseDto signUpResponseDto  = new SignUpResponseDto();;
        try{
            user = userService.signUp(requestDto.getEmailId(), requestDto.getPassword());

            signUpResponseDto.setRepsonseStatus(ResponseStatus.SUCCESS);
            signUpResponseDto.setUserId(user.getId());
        }catch (Exception e){
            signUpResponseDto.setRepsonseStatus(ResponseStatus.FAILURE);
            signUpResponseDto.setUserId(-1L);
        }
        return signUpResponseDto;
    }
}
