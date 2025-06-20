package application.controller;

import application.entity.Callback;
import application.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin({"${app.clients.ciafo.origin}"})
@RestController
@RequiredArgsConstructor
@Slf4j
final class CallbackController {
    private final EmailService emailService;

    @PostMapping("/api/callback")
    void callback(@RequestHeader("X-Caller-Id") String recipient, @RequestBody Callback callback) {
        log.info("Callback received for: [{}], with payload: [{}]", recipient, callback);
        emailService.send(
                recipient,
                "Someone is interested!",
                String.format(
                        "A visitor left the following contact: %s.<br>The product he was interested in is: <a href=%s>this</a>.",
                        callback.getAddress(),
                        callback.getProduct()));
    }
}