package com.niraj53.Junittesting.tddtesting.user;

import com.niraj53.Junittesting.tddtesting.dto.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

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
    void testFindByIdSuccess(){
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
    void testFindByIdNotFound(){
        doReturn(Optional.empty()).when(userRepository).findById(1);

        // Execute the service call
        final Optional<User> actualUser = userService.findById(1);

        // Assert the response
        assertFalse(actualUser.isPresent(), "User Not found");
    }
}
