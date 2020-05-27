package com.niraj53.Junittesting.tddtesting.user;

import com.niraj53.Junittesting.tddtesting.dto.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {

    /**
     * The service that we want to test.
     */
    @Autowired
    private UserService userService;

    /**
     * A mock version of the UserRepository for use in our tests.
     */
    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Test findById Success")
    void testFindByIdSuccess() {
        User mockUser = new User(1, "NIRAJ", "DESC");
        doReturn(Optional.of(mockUser)).when(userRepository).findById(1);

        // Execute the service call
        final Optional<User> actualUser = userService.findById(1);

        // Assert the response
        assertTrue(actualUser.isPresent(), "User found");
        assertSame(actualUser.get(), mockUser, "User should be same");
    }

    @Test
    @DisplayName("Test findById Not Found")
    void testFindByIdNotFound() {
        doReturn(Optional.empty()).when(userRepository).findById(1);

        // Execute the service call
        final Optional<User> actualUser = userService.findById(1);

        // Assert the response
        assertFalse(actualUser.isPresent(), "User Not found");
    }

    @Test
    @DisplayName("Test findAll Success")
    void testFindAllSuccess() {
        User mockUser1 = new User(1, "NIRAJ", "DESC");
        User mockUser2 = new User(2, "NIRAJ1", "DESC1");
        doReturn(Arrays.asList(mockUser1, mockUser2))
                .when(userRepository).findAll();

        // Execute the service call
        final List<User> actualUsers = userService.findAll();

        // Assert the response
        assertFalse(actualUsers.isEmpty(), "User should not empty");
        assertSame(actualUsers.size(), 2, "FindAll method should return true");
    }

    @Test
    @DisplayName("Test findAll with empty data")
    void testFindAllFailure() {
        doReturn(Collections.EMPTY_LIST).when(userRepository).findAll();

        // Execute the service call
        final List<User> actualUsers = userService.findAll();

        // Assert the response
        assertTrue(actualUsers.isEmpty(), "User should be empty");
    }

    @Test
    @DisplayName("Test save user success")
    void testSaveUserSuccess() {
        User mockUser = new User(1, "NIRAJ", "DESC");
        doReturn(mockUser).when(userRepository).save(any());

        // Execute the service call
        final User actualUser = userService.save(mockUser);

        // Assert the response
        assertNotNull(actualUser, "User should not be null");
        assertEquals(actualUser, mockUser, "User should be same with mock");
    }

    @Test
    @DisplayName("Test delete user success")
    void testDeleteUserSuccess() {
        doReturn(Boolean.TRUE).when(userRepository).delete(1);

        // Execute the service call
        final boolean isDeleteSuccessfully = userService.delete(1);

        // Assert the response
        assertTrue(isDeleteSuccessfully, "Response should be TRUE");
    }
}
