package ru.drdrapp.drappogram.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    private static final Logger LOGGER  = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public ModelAndView getLoginPage(ModelAndView model, HttpServletRequest request) {
        LOGGER.debug(">>> Login controller");
        if (request.getParameterMap().containsKey("error")) {
            model.addObject("message", "Ошибка ввода логина или пароля!");
        }
        return model;
    }
}