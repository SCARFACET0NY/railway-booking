package com.anton.railway.booking.service.impl;

import com.anton.railway.booking.auth.UserPrincipal;
import com.anton.railway.booking.entity.User;
import com.anton.railway.booking.exception.UserException;
import com.anton.railway.booking.repository.UserRepository;
import com.anton.railway.booking.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final long ID = 1L;
    private static final String USERNAME = "anton";
    private static final List<User> users = new ArrayList<>();
    private static final User user = new User();
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;

    @BeforeAll
    static void beforeAll() {
        User user1 = new User();
        user.setUserId(ID);
        user.setUsername(USERNAME);

        users.add(user);
        users.add(user1);
    }

    @Test
    void findAllTest() {
        when(userRepository.findAll()).thenReturn(users);
        List<User> returnedUsers = userService.findAll();

        assertNotNull(returnedUsers);
        assertEquals(2, returnedUsers.size());
        assertEquals(users, returnedUsers);

        verify(userRepository).findAll();
    }

    @Test
    void findByIdTest() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(user))
                .thenReturn(Optional.empty());
        User returnedUser = userService.findById(ID);

        assertNotNull(returnedUser);
        assertEquals(user, returnedUser);
        assertEquals(user.getUserId(), returnedUser.getUserId());
        assertEquals(user.getUsername(), returnedUser.getUsername());
        assertThrows(UserException.class, () -> userService.findById(ID));

        verify(userRepository, times(2)).findById(anyLong());
    }

    @Test
    void saveTest() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        assertEquals(userService.save(user), user);

        verify(userRepository).save(any(User.class));
    }

    @Test
    void deleteTest() {
        userService.delete(user);

        verify(userRepository).delete(any(User.class));
    }

    @Test
    void deleteByIdTest() {
        userService.deleteById(ID);

        verify(userRepository).deleteById(anyLong());
    }

    @Test
    void findByUsernameTest() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);
        userService.findByUsername(USERNAME);

        verify(userRepository).findByUsername(anyString());
    }

    @Test
    void loadUserByUsernameTest() {
        when(userRepository.findByUsername(anyString()))
                .thenReturn(user)
                .thenReturn(null);
        UserPrincipal principal = (UserPrincipal) userService.loadUserByUsername(USERNAME);

        assertNotNull(principal);
        assertEquals(user.getUserId(), principal.getUser().getUserId());
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(USERNAME));

        verify(userRepository, times(2)).findByUsername(anyString());
    }
}