package com.arun.user.service.repositories;

import com.arun.user.service.services.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User , String> {

}
