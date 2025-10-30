package application.controller;

import application.configuration.utils.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
class ViewControllers implements WebMvcConfigurer {
    private final Properties properties;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addRedirectViewController("/grafana", properties.getServices().get("grafana").getUrl());
    }
}