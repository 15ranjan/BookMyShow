package com.example.bookmyshow.services;

import com.example.bookmyshow.exceptions.InvalidShowException;
import com.example.bookmyshow.exceptions.InvalidUserException;
import com.example.bookmyshow.exceptions.ShowSeatNotAvailableException;
import com.example.bookmyshow.models.*;
import com.example.bookmyshow.repositories.ShowRepository;
import com.example.bookmyshow.repositories.ShowSeatRepository;
import com.example.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private PriceCalculator priceCalculator;

    @Autowired
    BookingService(UserRepository userRepository, ShowRepository showRepository,
                   ShowSeatRepository showSeatRepository, PriceCalculator priceCalculator){
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.priceCalculator = priceCalculator;
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId, Long showId, List<Long> showSeatIds) throws InvalidUserException,
                InvalidShowException, ShowSeatNotAvailableException {
        //Steps -
        // --------TAKE A LOCK (Approach 1) ------
        //1. Get user with a userId.
        //2. Get show with a showId.
        //3. Get showSeat with showSeatId.
        //   -------- TAKE A LOCK (Approach 2)  ------------
        //4. Check if seats are available or not.
        //5  If no, throw an EXCEPTION.
        //6. If yes, Mark the seat status as BLOCKED.
        //7. Save the updated status in the Database.
        //   ------- RELEASE A LOCK (Approach 2)---------
        //8. Create the booking object with PENDING status.
        //9. Return the booking object.
        //  ------- RELEASE A LOCK (Approach 1)---------


        // We are following approach 1 (Taking a lock from starting steps of booking);
        //1. Get user with a userId
        Optional<User> optionalUser = userRepository.findById(userId);

        // means if the bucket of optional is empty.
        if (optionalUser.isEmpty()) {
            throw new InvalidUserException("Invalid User");
        }
        User user = optionalUser.get();

        //2. Get show with a showId.
        Optional<Show> optionalShow = showRepository.findById(showId);

        if (optionalShow.isEmpty()) {
            throw new InvalidShowException("Invalid Show");
        }
        Show show = optionalShow.get();
        //3. Get showSeat with showSeatId.
        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);
        // Select * from showSeat where id IN {1,2,3,4}
        // NO Optional used, because list cannot be null, we will get an empty list if there is no show seat available.

        //4. Check if seats are available or not.
        for (ShowSeat showSeat : showSeats) {
            //5  If no, throw an EXCEPTION.
            if (!showSeat.getSeatStatus().equals(SeatStatus.AVAILABLE)) {
                throw new ShowSeatNotAvailableException("No Seat available");
            }
        }
        List<ShowSeat> finalShowSeats  = new ArrayList<>();

        //6. If yes, Mark the seat status as BLOCKED.
        for(ShowSeat showSeat : showSeats){
            showSeat.setSeatStatus(SeatStatus.BLOCKED);
            //7. Save the updated status in the Database.
            showSeatRepository.save(showSeat);
            finalShowSeats.add(showSeatRepository.save(showSeat));
        }

        //8. Create the booking object with PENDING status.
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setTimeOfBooking(new Date());
        booking.setShow(show);
        booking.setUser(user);
        booking.setSeats(finalShowSeats);
        booking.setPayments(new ArrayList<>());
        booking.setPrice(priceCalculator.calculatePrice(show, finalShowSeats));

        return booking;
    }
}
