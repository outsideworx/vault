package application.controller;

import org.springframework.web.servlet.ModelAndView;

public interface ModelVisitor {
    ModelAndView getModel();
}
