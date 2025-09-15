package application.controller.clients;

import application.controller.ModelVisitor;
import application.converter.clients.SoupArtConverter;
import application.entity.clients.SoupArtItem;
import application.entity.clients.mapping.SoupArtThumbnails;
import application.repository.clients.SoupArtRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
class SoupArtController implements ModelVisitor {
    private final SoupArtConverter soupArtConverter;

    private final SoupArtRepository soupArtRepository;

    @CacheEvict(value = "soupArtItems", allEntries = true)
    @PostMapping("/soupart")
    public String submit(@RequestParam String category, @RequestParam Map<String, String> params, @RequestParam Map<String, MultipartFile> files) {
        log.info("Upload processor starts for: soupart");
        List<SoupArtItem> items = soupArtConverter.processItems(category, params, files);
        soupArtRepository.saveAll(soupArtConverter.filterItemsToInsert(items));
        soupArtConverter.filterItemsToUpdate(items).forEach(soupArtRepository::update);
        soupArtRepository.deleteAllById(soupArtConverter.filterIdsToDelete(items));
        return "redirect:/";
    }

    @Override
    public ModelAndView getModel() {
        ModelAndView model = new ModelAndView("clients/soupart");
        List<String> categories = List.of(
                "Gallery");
        Map<String, List<SoupArtThumbnails>> items = categories
                .stream()
                .collect(Collectors.toMap(Function.identity(), soupArtRepository::getThumbnailsByCategory));
        model.addObject("items", items);
        model.addObject("categories", categories);
        return model;
    }
}