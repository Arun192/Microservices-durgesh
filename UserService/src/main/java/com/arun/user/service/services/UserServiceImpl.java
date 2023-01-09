package com.arun.user.service.services;

import com.arun.user.service.external.services.HotelService;
import com.arun.user.service.services.entities.Hotel;
import com.arun.user.service.services.entities.Rating;
import com.arun.user.service.services.entities.User;
import com.arun.user.service.exceptions.ResourceNotFoundException;
import com.arun.user.service.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {

        //generate unique userId
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return this.userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {

        //get user from database with user repository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given Id is not found on server " + userId));

        //fetch rating of the above user from RATING SERVICE
        //http://localhost:8083/ratings/users/1da7d559-5238-4d87-ab7b-ed18f14ff445
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);
        logger.info("{}", ratingsOfUser);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).collect(Collectors.toList());

         user.setRatings(ratings);

        List<Rating> ratingList = ratings.stream().map(rating -> {
            //api call to hotel service to get the hotel
            //http://localhost:8082/hotels/3bc5cda1-4c87-43fb-8e08-1d20ca432546
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/3bc5cda1-4c87-43fb-8e08-1d20ca432546", Hotel.class);
//            Hotel hotel = forEntity.getBody();

            Hotel hotel =hotelService.getHotel(rating.getHotelId());
           // logger.info("response status code {}", forEntity.getStatusCode());

            //set the hotel to rating
            rating.setHotel(hotel);
            //return the rating
            return rating;
        }).collect(Collectors.toList());

        return user;
    }
}
