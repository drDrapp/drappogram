package ru.drdrapp.drappogram.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.drdrapp.drappogram.Utils.DgUtils;
import ru.drdrapp.drappogram.models.DgUser;
import ru.drdrapp.drappogram.services.DgUserService;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final DgUserService dgUserService;

    @GetMapping("/registration")
    public ModelAndView registration(Authentication authentication, ModelAndView model) {
        if (authentication != null) {
            if (authentication.isAuthenticated()) {
                model.setViewName("home");
            } else {
                model.setViewName("registration");
            }
        } else {
            model.setViewName("registration");
        }
        return model;
    }

    @PostMapping("/registration")
    public ModelAndView registerUser(@RequestParam("passwordToConfirm") String passwordToConfirm,
                                     @Valid DgUser dgUser,
                                     BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();
        model.setViewName("registration");
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = DgUtils.getErrors(bindingResult);
            model.addObject("errorsMap", errorsMap);
            model.addObject("dgUser", dgUser);
        } else {
            if (!dgUser.getPassword().equals(passwordToConfirm)) {
                model.addObject("message", "Пароли не совпадают!");
                return model;
            }
            if (dgUserService.registerUser(dgUser)) {
                model.setViewName("login");
                model.addObject("message", "Регистрация прошла успешно!");
                model.addObject("messageType", "success");
                model.addObject("dgUser", null);
            } else {
                model.addObject("message", "Такой пользователь уже существует!");
            }
        }
        return model;
    }

    @GetMapping("/activate/{code}")
    public ModelAndView activateUser(@PathVariable String code) {
        ModelAndView model = new ModelAndView("login");
        if (dgUserService.activateUser(code)) {
            model.addObject("message", "Активация пользователя успешно завершена!");
            model.addObject("messageType", "success");
        } else {
            model.addObject("message", "Пользователь с таким кодом активации не найден!");
        }
        return model;
    }

}