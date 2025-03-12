package application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
class HomeController {
    private final Pattern domainPattern = Pattern.compile("(?<=@)[^.]+(?=\\.)");

    private final List<ModelVisitor> models;

    @GetMapping("/home")
    ModelAndView home() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Matcher matcher = domainPattern.matcher(email);
        if (matcher.find()) {
            return getModel("client/".concat(matcher.group(0)))
                    .orElseThrow(() -> new UsernameNotFoundException("Client view is not implemented."));
        }
        throw new UsernameNotFoundException("Invalid email address.");
    }

    private Optional<ModelAndView> getModel(String viewName) {
        return models
                .stream()
                .map(ModelVisitor::getModel)
                .filter(model -> viewName.equals(model.getViewName()))
                .findAny();
    }
}