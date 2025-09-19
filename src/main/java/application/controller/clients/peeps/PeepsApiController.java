package application.controller.clients.peeps;

import application.entity.clients.peeps.PeepsItem;
import application.repository.clients.PeepsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("${app.clients.peeps.origin}")
@RestController
@RequiredArgsConstructor
@Slf4j
final class PeepsApiController {
    private final PeepsRepository peepsRepository;

    @GetMapping("/api/gaiapeeps")
    List<PeepsItem> getPeeps() {
        log.info("Incoming API request: gaiapeeps");
        return peepsRepository.findAll();
    }
}