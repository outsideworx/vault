package application.controller.client;

import application.controller.ModelCollector;
import application.entity.client.CiafoForm;
import application.repository.client.CiafoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
class CiafoController implements ModelCollector {
    private final CiafoRepository ciafoRepository;

    @PostMapping("/home/{category}")
    ModelAndView handleFormSubmit(@PathVariable String category, @ModelAttribute List<CiafoForm> items) {
        return new ModelAndView("client/come-in-and-find-out");
    }

    @Override
    public ModelAndView getModel() {
        ModelAndView model = new ModelAndView("client/come-in-and-find-out");
        Map<String, List<CiafoForm>> items = Map.of(
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
