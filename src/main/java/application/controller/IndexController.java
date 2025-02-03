package application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
final class IndexController {
    @GetMapping("/")
    String index() {
        return "Hello world!?";
    }
}