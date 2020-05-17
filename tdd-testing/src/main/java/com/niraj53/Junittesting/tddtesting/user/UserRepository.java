package com.niraj53.Junittesting.tddtesting.user;

import com.niraj53.Junittesting.tddtesting.dto.User;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

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
