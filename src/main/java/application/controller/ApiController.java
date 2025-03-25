package application.controller;

import application.entity.client.mapping.CiafoFirstImage;
import application.entity.client.mapping.CiafoImages;
import application.repository.client.CiafoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiController {
    private final CiafoRepository ciafoRepository;

    @GetMapping("/api/come-in-and-find-out/categories/{category}")
    List<CiafoFirstImage> category(@PathVariable String category, @RequestParam int offset) {
        log.info("Incoming API request for category: {} with offset: {}", category, offset);
        return ciafoRepository.getFirstImagesByCategoryAndOffset(category, offset);
    }

    @GetMapping("/api/come-in-and-find-out/{id}")
    CiafoImages details(@PathVariable Long id) {
        log.info("Incoming API request for ID: {}", id);
        return ciafoRepository.getImagesById(id);
    }
}