package ru.drdrapp.drappogram.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private static final ch.qos.logback.classic.Logger LOGGER =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public ModelAndView getLoginPage(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("login");
        LOGGER.debug(">>> Login controller");
        if (request.getParameterMap().containsKey("error")) {
            model.addObject("message", "Ошибка ввода логина или пароля!");
        }
        return model;
    }
}