package com.example.bookmyshow.services;

import com.example.bookmyshow.models.Show;
import com.example.bookmyshow.models.ShowSeat;
import com.example.bookmyshow.models.ShowSeatType;
import com.example.bookmyshow.repositories.ShowSeatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PriceCalculator {

    private ShowSeatTypeRepository showSeatTypeRepository;
    @Autowired
    private PriceCalculator(ShowSeatTypeRepository showSeatRepository){
        this.showSeatTypeRepository = showSeatTypeRepository;
    }
    public int calculatePrice(Show show, List<ShowSeat> showSeats){
        // 1. Get the ShowSeatTypes for the input show.
        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show);

        // 2. Get the type of input ShowSeats.
        int amount = 0;
        for(ShowSeat showSeat : showSeats){
            for(ShowSeatType showSeatType : showSeatTypes){
                if(showSeat.getSeat().getSeatType().equals(showSeatType.getSeatType())){
                    amount += showSeatType.getPrice();
                    break;
                }
            }
        }

        // 3. Add the corresponding price.
        return amount;
    }
}
