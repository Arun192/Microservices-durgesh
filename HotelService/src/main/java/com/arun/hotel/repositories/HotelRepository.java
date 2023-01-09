package com.arun.hotel.repositories;

import com.arun.hotel.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel ,String> {

}
