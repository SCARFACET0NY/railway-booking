package com.anton.railway.booking.service;

import com.anton.railway.booking.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends CrudService<User, Long>, UserDetailsService {
    User findByUsername(String username);
}