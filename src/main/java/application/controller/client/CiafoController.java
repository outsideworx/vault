package application.controller.client;

import application.controller.ModelVisitor;
import application.converter.client.CiafoConverter;
import application.entity.client.CiafoItem;
import application.entity.client.CiafoThumbnails;
import application.repository.client.CiafoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
class CiafoController implements ModelVisitor {
    private final CiafoConverter ciafoConverter;

    private final CiafoRepository ciafoRepository;

    @CacheEvict(value = "items", allEntries = true)
    @PostMapping("/come-in-and-find-out/{category}")
    public String submit(@PathVariable String category, @RequestParam Map<String, String> params, @RequestParam Map<String, MultipartFile> files) {
        List<CiafoItem> items = ciafoConverter.processItems(category, params, files);
        ciafoRepository.saveAll(ciafoConverter.filterItemsToInsert(items));
        ciafoConverter.filterItemsToUpdate(items).forEach(ciafoRepository::update);
        ciafoConverter.filterIdsToDelete(items).forEach(id -> ciafoRepository.deleteByCategoryAndId(category, id));
        return "redirect:/";
    }

    @Override
    public ModelAndView getModel() {
        ModelAndView model = new ModelAndView("client/come-in-and-find-out");
        List<String> categories = List.of(
                "Furniture",
                "Clothing",
                "Jewelry",
                "Accessories",
                "Art-Print",
                "Asiatica",
                "Curiosity");
        Map<String, List<CiafoThumbnails>> items = categories
                .parallelStream()
                .collect(Collectors.toMap(Function.identity(), ciafoRepository::getThumbnailsByCategory));
        model.addObject("items", items);
        model.addObject("categories", categories);
        return model;
    }
}