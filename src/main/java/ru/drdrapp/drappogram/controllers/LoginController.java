package ru.drdrapp.drappogram.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.drdrapp.drappogram.models.DgUser;
import ru.drdrapp.drappogram.repositories.DgUserRepository;

import java.util.Optional;

@Controller
public class LoginController {

    private static final Logger LOGGER  = LoggerFactory.getLogger(LoginController.class);

    private final DgUserRepository dgUserRepository;

    public LoginController(DgUserRepository dgUserRepository) {
        this.dgUserRepository = dgUserRepository;
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage(HttpServletRequest request) {

        Optional<DgUser> dgUserList = dgUserRepository.findByLogin("z");




        ModelAndView model = new ModelAndView("login");
        LOGGER.debug(">>> Login controller");
        if (request.getParameterMap().containsKey("error")) {
            model.addObject("message", "Ошибка ввода логина или пароля!");
        }
        return model;
    }
}