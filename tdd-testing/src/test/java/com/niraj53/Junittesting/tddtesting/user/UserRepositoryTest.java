package com.niraj53.Junittesting.tddtesting.user;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import com.niraj53.Junittesting.tddtesting.dto.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({DBUnitExtension.class, SpringExtension.class})
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserRepository userRepository;

    public ConnectionHolder getConnectionHolder() {
        return () -> dataSource.getConnection();
    }

    @Test
    @DataSet("users.yml")
    void testFindAll() {
        List<User> users = userRepository.findAll();
        assertEquals(2, users.size(), "Expected 2 users in the database");
    }

    @Test
    @DataSet("users.yml")
    void testFindById() {
        Optional<User> user = userRepository.findById(1);

        //Validate that we found it
        assertTrue(user.isPresent(), "User with ID 1 should be found");

        // Validate the user values
        User u = user.get();
        assertEquals(u.getId(), 1, "User ID should be 1");
        assertEquals(u.getName(), "niraj1", "User NAME should be \"niraj1\"");
    }

    @Test
    @DataSet("users.yml")
    void testFindByIdNotFound() {
        Optional<User> user = userRepository.findById(3);

        //Validate that we found it
        assertFalse(user.isPresent(), "User with ID 3 should not be found");
    }

    @Test
    @DataSet("users.yml")
    void testDeleteUserSuccess() {
        boolean isDeleteSuccess = userRepository.delete(1);
        assertTrue(isDeleteSuccess, "Delete should return true on success");

        Optional<User> user = userRepository.findById(1);
        assertFalse(user.isPresent(), "User with ID 1 should have been deleted");
    }

    @Test
    @DataSet("users.yml")
    void testSaveUserSuccess() {
        User mockUser = new User(4, "TEST NIRAJ 4", "TEST DESC 4");
        User savedUser = userRepository.save(mockUser);

        //Validate the saved user
        assertEquals("TEST NIRAJ 4", savedUser.getName(), "User NAME should be \"TEST NIRAJ 4\"");
        assertEquals(4, savedUser.getId(), "User ID should be 4");

        // Validate that we can get it back out of the database
        Optional<User> loadedUser = userRepository.findById(savedUser.getId());
        assertTrue(loadedUser.isPresent(), "Could not reload User from database");
        assertEquals("TEST NIRAJ 4", loadedUser.get().getName(), "User Name does not match");
        assertEquals(4, loadedUser.get().getId(), "");
    }
}
