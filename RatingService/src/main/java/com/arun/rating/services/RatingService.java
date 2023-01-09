package com.arun.rating.services;

import com.arun.rating.entities.Rating;

import java.util.List;


public interface RatingService {

    // create
    Rating create(Rating rating);

    // getAll Ratings
    List<Rating> getAllRatings();

    //getAll by UserId
    List<Rating> getRatingByUserId(String userId);

    //getAll by Hotel
    List<Rating> getRatingByHotelId(String hotelId);

}
