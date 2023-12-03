package com.example.bookmyshow.repositories;

import com.example.bookmyshow.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    @Override
    List<ShowSeat> findAllById(Iterable<Long> longs);

    @Override
    ShowSeat save(ShowSeat showSeat);
    // It behaves just like inserting and updating the database.
    // INSERT - If the input showSeat object does not have the id value, then it will be like insert call.
    // And it will be return the show seat object with id.
    // UPDATE - If the input showSeat object has already an Id value, it will be an update call,
    // and it will return an updated showSeat object.

}
