package application.controller.clients;

import application.entity.Callback;
import application.entity.clients.mapping.CiafoFirstImage;
import application.entity.clients.mapping.CiafoImages;
import application.repository.clients.CiafoRepository;
import application.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@CrossOrigin(origins = "${app.clients.ciafo.origin}")
@RestController
@RequiredArgsConstructor
@Slf4j
class CiafoApiController {
    private final CiafoRepository ciafoRepository;

    private final EmailService emailService;

    @Value("${app.clients.ciafo.token}")
    private String ciafoAuthToken;

    @GetMapping("/api/come-in-and-find-out")
    List<CiafoFirstImage> getCiafoFirstImages(@RequestParam String category, @RequestParam int offset, @RequestHeader(value = "X-Auth-Token") String authToken) {
        log.info("Incoming API request for category: {} with offset: {}", category, offset);
        authTokenCheck(authToken);
        return ciafoRepository.getFirstImagesByCategoryAndOffset(category, offset);
    }

    @GetMapping("/api/cached/come-in-and-find-out")
    CiafoImages getCiafoImages(@RequestParam Long id, @RequestHeader(value = "X-Auth-Token") String authToken) {
        log.info("Incoming API request for ID: {}", id);
        authTokenCheck(authToken);
        return ciafoRepository.getImagesById(id);
    }

    @PostMapping("/api/callback")
    void callback(@RequestParam String username, @RequestBody Callback callback, @RequestHeader(value = "X-Auth-Token") String authToken) {
        log.info("Callback received for [{}] with payload: {}", username, callback);
        authTokenCheck(authToken);
        emailService.send(
                username,
                "Someone is interested!",
                String.format(
                        "A visitor left the following contact: %s.<br>The product he was interested in is: <a href=%s>this</a>.",
                        callback.getAddress(),
                        callback.getProduct()));
    }

    private void authTokenCheck(String authToken) {
        if (StringUtils.isEmpty(authToken) || !ciafoAuthToken.equals(authToken)) {
            String errorMessage = "Missing or invalid auth token.";
            log.error(errorMessage);
            throw new BadCredentialsException(errorMessage);
        }
    }
}