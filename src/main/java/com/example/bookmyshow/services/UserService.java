package com.example.bookmyshow.services;

import com.example.bookmyshow.exceptions.InvalidUserException;
import com.example.bookmyshow.models.User;
import com.example.bookmyshow.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public User signUp(String emailId, String password) throws InvalidUserException {
        Optional<User> optionalUser = userRepository.findByEmail(emailId);

        // if the user is present in database, go to login workflow
        // else call the signup workflow
        if(optionalUser.isPresent()){
            login(emailId, password);
        }

        User user = new User();
        user.setBooking(new ArrayList<>());
        user.setEmail(emailId);

        // before we save the password to database, we should encrypt it using BCrypt Encoder.
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(password));

        // save the user to the database;
        return userRepository.save(user);
    }

    public boolean login(String emailId, String password){

        Optional<User> optionalUser = userRepository.findByEmail(emailId);
        String passwordStoredInDB = optionalUser.get().getPassword();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        return bCryptPasswordEncoder.matches(password, passwordStoredInDB);
    }
}
