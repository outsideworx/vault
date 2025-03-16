package application.controller.client;

import application.controller.ModelVisitor;
import application.converter.client.CiafoConverter;
import application.entity.client.CiafoItem;
import application.repository.client.CiafoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
class CiafoController implements ModelVisitor {
    private final CiafoConverter ciafoConverter;

    private final CiafoRepository ciafoRepository;

    @PostMapping("/come-in-and-find-out/{category}")
    String submit(@PathVariable String category, @RequestParam Map<String, String> params, @RequestParam Map<String, MultipartFile> files) {
        List<CiafoItem> items = ciafoConverter.processItems(category, params, files);
        ciafoRepository.saveAll(ciafoConverter.filterItemsToInsert(items));
        ciafoConverter.filterItemsToUpdate(items).forEach(ciafoRepository::update);
        ciafoConverter.filterIdsToDelete(items).forEach(id -> ciafoRepository.deleteByCategoryAndId(category, id));
        return "redirect:/";
    }

    @Override
    public ModelAndView getModel() {
        ModelAndView model = new ModelAndView("client/come-in-and-find-out");
        Map<String, List<CiafoItem>> items = Stream.of(
                        "Furniture",
                        "Clothing",
                        "Jewelry",
                        "Accessories",
                        "Art-Print",
                        "Asiatica",
                        "Curiosity")
                .collect(Collectors.toMap(Function.identity(), ciafoRepository::getByCategory));
        model.addObject("items", items);
        List<String> categories = new ArrayList<>(items.keySet());
        Collections.sort(categories);
        model.addObject("categories", categories);
        return model;
    }
}