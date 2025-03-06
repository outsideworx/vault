package application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
class HomeController {
    private final Pattern addressDomainPattern = Pattern.compile("(?<=@)[^.]+(?=\\.)");

    @GetMapping("/home")
    ModelAndView home() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Matcher matcher = addressDomainPattern.matcher(email);
        if (!matcher.find()) {
            throw new UsernameNotFoundException("Invalid email address.");
        }
        return new ModelAndView(matcher.group(0));
    }
}
