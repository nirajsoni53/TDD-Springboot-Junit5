package com.niraj53.Junittesting.tddtesting.user;

import com.niraj53.Junittesting.tddtesting.dto.User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    public String helloWorld() {
        return "Hello world";
    }

    public Optional<User> findById(int userId) {
        return null;
    }

    public User save(User user) {
        return user;
    }

    public boolean delete(int id) {
        return true;
    }

    public List<User> findAll() {
        return Collections.emptyList();
    }
}
