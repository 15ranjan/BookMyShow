package com.example.bookmyshow.repositories;

import com.example.bookmyshow.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    @Override
    Optional<User> findById(Long aLong); // finding the user with using the user id.

    Optional<User> findByEmail(String email);
    // SELECT * from USER where Id = 0;
    // SELECT * from USER where Id = 1;

    @Override
    User save(User user);
}

/*
To create any repositories, follow 2 steps -

Why repositories are needed - Repositories are helper class or interface which helps us to interact with the models.
So that the data can be retrieved from DataBase or the particular entity. In Java JPA repository is a popular interface used for the same
purpose.
1. Make the repository as an Interface.
2. Make te repository interface extends JPA repository interface. At the time of spring initializer, Spring JPA repo
have been imported.
     finding the user with userId
    // Select * from users where userId = xxx
    // select * from users where email = xxx

    Where query gets handled internally by JPA.
    JpaRepository<User, Long> By creating this repo, we are notifying Spring that this repository has been created to
    interact with User model and its id which is a primary key.
    We use optional keyword to make our code null pointer exception safe.


 */
