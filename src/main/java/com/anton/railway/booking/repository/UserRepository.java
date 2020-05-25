package com.anton.railway.booking.repository;

import com.anton.railway.booking.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
