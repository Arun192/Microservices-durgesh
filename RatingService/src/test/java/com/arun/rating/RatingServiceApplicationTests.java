package com.arun.rating;

import com.arun.rating.entities.Rating;
import com.arun.rating.services.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class RatingServiceApplicationTests {

    @Autowired
    private RatingService ratingService;
    @Test
    void contextLoads() {
    }

    @Test
    void createRating(){

        Rating rating = Rating.builder()
                .rating(10)
                .userId("")
                .hotelId("")
                .feedBack("This is created using feign Client ")
                .build();
  Rating ratingResponseEntity = ratingService.create(rating);

        System.out.println("new Rating created !!");
    }

}
