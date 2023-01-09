package com.arun.user.service.controllers;

import com.arun.user.service.services.UserService;
import com.arun.user.service.services.entities.User;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User user1 = userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }
    int retryCount = 1;
    //single user
    @GetMapping("/{userId}")
    // @CircuitBreaker(name = "RATING_HOTEL_BREAKER",fallbackMethod = "ratingHotelFallBack")
   // @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallBack")
    @RateLimiter(name = "userRateLimiter" , fallbackMethod = "ratingHotelFallBack")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
        log.info("Get Single User Handler : UserController");
        log.info("Retry count: {} ", retryCount);
        retryCount++;
        User user = this.userService.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }



    //Creating FallBack Method For Circuit Breaker
    public ResponseEntity<User> ratingHotelFallBack(String userId, Exception ex) {
        //log.info("Fallback is executed because service is down {}: "+ex.getMessage());

        User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This User is dummy because some services is down !!!")
                .userId("7836423")
                .build();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    //all user get
    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {

        List<User> allUsers = this.userService.getAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(allUsers);
    }


}
