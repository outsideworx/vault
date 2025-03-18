package application.controller;

import application.entity.client.CiafoItem;
import application.repository.client.CiafoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiController {
    private final CiafoRepository ciafoRepository;

    @GetMapping("/api/ciafo/{category}")
    List<CiafoItem> index(@PathVariable String category, @RequestParam int page) {
        return ciafoRepository.getByCategory(category, PageRequest.of(page, 6, Sort.by("id").ascending()));
    }
}