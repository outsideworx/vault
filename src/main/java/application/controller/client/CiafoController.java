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

@Controller
@RequiredArgsConstructor
class CiafoController implements ModelVisitor {
    private final CiafoConverter ciafoConverter;

    private final CiafoRepository ciafoRepository;

    @PostMapping("/come-in-and-find-out/{category}")
    String submit(@PathVariable String category, @RequestParam Map<String, String> params, @RequestParam Map<String, MultipartFile> files) {
        ciafoRepository.saveAll(ciafoConverter.processRequestParams(category, params, files));
        ciafoConverter.getIdsToDelete(params).forEach(id -> ciafoRepository.deleteByCategoryAndId(category, id));
        return "redirect:/home";
    }

    @Override
    public ModelAndView getModel() {
        ModelAndView model = new ModelAndView("client/come-in-and-find-out");
        Map<String, List<CiafoItem>> items = Map.of(
                "Furniture", ciafoRepository.getByCategory("Furniture"),
                "Clothing", ciafoRepository.getByCategory("Clothing"),
                "Jewelry", ciafoRepository.getByCategory("Jewelry"),
                "Accessories", ciafoRepository.getByCategory("Accessories"),
                "Art/Print", ciafoRepository.getByCategory("Art/Print"),
                "Asiatica", ciafoRepository.getByCategory("Asiatica"),
                "Curiosity", ciafoRepository.getByCategory("Curiosity")
        );
        model.addObject("items", items);
        List<String> categories = new ArrayList<>(items.keySet());
        Collections.sort(categories);
        model.addObject("categories", categories);
        return model;
    }
}