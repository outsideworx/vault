package application.controller;

import application.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequiredArgsConstructor
final class LoginController {
    private final UserService userService;

    @GetMapping("/admin")
    ModelAndView index() {
        return new ModelAndView("admin");
    }

    @PostMapping("/admin")
    ModelAndView login(@RequestParam String username, @RequestParam String password) {
        try {
            if (!userService.loadUserByUsername(username).getPassword().equals(password)) {
                throw new BadCredentialsException("Invalid username or password.");
            }
        } catch (UsernameNotFoundException | BadCredentialsException ignore) {
            ModelAndView login = new ModelAndView("admin");
            login.addObject("error", true);
            log.error("Unsuccessful login try username[{}], password[{}].", username, password);
            return login;
            }
        return new ModelAndView("home");
    }
}