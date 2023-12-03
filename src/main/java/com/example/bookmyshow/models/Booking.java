package com.example.bookmyshow.models;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity  // Entity annotations are used for the classes for which we want to create a table in DB.
@EntityListeners(AuditingEntityListener.class)
public class Booking extends BaseModel{

    @ManyToMany
    private List<ShowSeat> seats;
    @ManyToOne
    private Show show;

    @OneToMany
    private List<Payment> payments;

    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;

    @ManyToOne
    private User user;
    private int price;
    private Date timeOfBooking;


}
