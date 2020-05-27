package com.niraj53.Junittesting.tddtesting.user;

import com.niraj53.Junittesting.tddtesting.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Returns the user with the specified id.
     *
     * @param id  ID of the user to retrieve.
     * @return    The requested User if found
     */
    public Optional<User> findById(int id) { return userRepository.findById(id); }

    /**
     * Saves the specified user to database.
     *
     * @param user    The user to save to the database.
     * @return        The saved product.
     */
    public User save(User user) { return userRepository.save(user); }

    /**
     * Deletes the product with the specified ID.
     *
     * @param id      The id of user to delete.
     * @return        TRUE if operation was successful.
     */
    public boolean delete(int id) { return userRepository.delete(id); }

    /**
     * Return all users in database.
     *
     * @return  All users in database.
     */
    public List<User> findAll() { return userRepository.findAll(); }
}
