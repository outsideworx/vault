package application.controller;

import application.entity.security.User;
import application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
final class UserController {
    private final UserService userService;

    @PostMapping("/users")
    void save(@RequestBody User user) {
        userService.save(user);
    }
}