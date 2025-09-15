package application.controller.clients;

import application.entity.clients.mapping.SoupArtFirstImage;
import application.entity.clients.mapping.SoupArtImages;
import application.repository.clients.SoupArtRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("${app.clients.soupart.origin}")
@RestController
@RequiredArgsConstructor
@Slf4j
final class SoupArtApiController {
    private final SoupArtRepository soupArtRepository;

    @GetMapping("/api/soupart")
    List<SoupArtFirstImage> getSoupArtFirstImages(@RequestParam String category, @RequestParam int offset) {
        log.info("Incoming API request for category: [{}], with offset: [{}]", category, offset);
        return soupArtRepository.getFirstImagesByCategoryAndOffset(category, offset);
    }

    @GetMapping("/api/cached/soupart")
    SoupArtImages getSoupArtImages(@RequestParam Long id) {
        log.info("Incoming API request for ID: [{}]", id);
        return soupArtRepository.getImagesById(id);
    }
}