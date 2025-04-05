package application.controller.client;

import application.entity.Callback;
import application.entity.client.mapping.CiafoFirstImage;
import application.entity.client.mapping.CiafoImages;
import application.repository.client.CiafoRepository;
import application.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "${cors.origins.ciafo}")
@RestController
@RequiredArgsConstructor
@Slf4j
public class CiafoApiController {
    private final CiafoRepository ciafoRepository;

    private final EmailService emailService;

    @GetMapping("/api/come-in-and-find-out/categories/{category}")
    List<CiafoFirstImage> getCiafoFirstImages(@PathVariable String category, @RequestParam int offset) {
        log.info("Incoming API request for category: {} with offset: {}", category, offset);
        return ciafoRepository.getFirstImagesByCategoryAndOffset(category, offset);
    }

    @GetMapping("/api/cached/come-in-and-find-out/{id}")
    CiafoImages getCiafoImages(@PathVariable Long id) {
        log.info("Incoming API request for ID: {}", id);
        return ciafoRepository.getImagesById(id);
    }

    @PostMapping("/api/callback/{username}")
    void callback(@PathVariable String username, @RequestBody Callback callback) {
        log.info("Callback received for [{}] with payload: {}", username, callback);
        emailService.send(
                username,
                "Someone is interested!",
                String.format(
                        "A visitor left the following contact: %s.<br>The product he was interested in is: <a href=%s>this</a>.",
                        callback.getAddress(),
                        callback.getProduct()));
    }
}