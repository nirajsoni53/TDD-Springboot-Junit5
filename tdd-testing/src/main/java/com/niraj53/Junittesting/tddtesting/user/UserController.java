package com.niraj53.Junittesting.tddtesting.user;

import com.niraj53.Junittesting.tddtesting.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findById/{userId}")
    public ResponseEntity<User> findById(@PathVariable("userId") int userId) {
        return userService
                .findById(userId)
                .map(ok()::body)
                .orElse(notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody User user) {
        User savedUser = userService.save(user);
        try {
            return created(new URI("/getUserById/" + savedUser.getId()))
                    .body(savedUser);
        } catch (URISyntaxException | NullPointerException e) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        return userService.findById(id)
                .map(u -> (userService.delete(id)
                        ? ok()
                        : status(HttpStatus.INTERNAL_SERVER_ERROR)).build())
                .orElse(notFound().build());
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAll() {
        return ok().body(userService.findAll());
    }
}
