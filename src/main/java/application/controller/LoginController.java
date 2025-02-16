package application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
final class LoginController {
    @GetMapping("/login")
    ModelAndView index() {
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    ModelAndView login(@RequestParam String username, @RequestParam String password) {
        if (username.equals("asd@asd")) {
            ModelAndView login = new ModelAndView("login");
            login.addObject("error", true);
            return login;
        }
        return new ModelAndView("home");
    }
}