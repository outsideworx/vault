package application.controller.client;

import application.controller.ModelVisitor;
import application.entity.client.CiafoItem;
import application.repository.client.CiafoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
class CiafoController implements ModelVisitor {
    private final CiafoRepository ciafoRepository;

    @PostMapping("/come-in-and-find-out/{category}")
    String submit(@PathVariable String category, @RequestParam Map<String, String> requestParams) {
        return "redirect:/home";
    }

    @Override
    public ModelAndView getModel() {
        ModelAndView model = new ModelAndView("client/come-in-and-find-out");
        Map<String, List<CiafoItem>> items = Map.of(
                "Furniture", ciafoRepository.getItemsForCategory("Furniture"),
                "Clothing", ciafoRepository.getItemsForCategory("Clothing"),
                "Jewelry", ciafoRepository.getItemsForCategory("Jewelry"),
                "Accessories", ciafoRepository.getItemsForCategory("Accessories"),
                "Art/Print", ciafoRepository.getItemsForCategory("Art/Print"),
                "Asiatica", ciafoRepository.getItemsForCategory("Asiatica"),
                "Curiosity", ciafoRepository.getItemsForCategory("Curiosity")
        );
        model.addObject("items", items);
        List<String> categories = new ArrayList<>(items.keySet());
        Collections.sort(categories);
        model.addObject("categories", categories);
        return model;
    }
}