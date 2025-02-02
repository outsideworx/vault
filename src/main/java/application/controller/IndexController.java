package application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
final class IndexController {
    @GetMapping("/")
    String hello() {
        return "Hello world!";
    }
}