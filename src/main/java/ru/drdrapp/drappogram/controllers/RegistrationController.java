package ru.drdrapp.drappogram.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.drdrapp.drappogram.froms.UserForm;
import ru.drdrapp.drappogram.services.DgUserServiceImpl;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final DgUserServiceImpl dgUserService;

    @GetMapping("/registration")
    public ModelAndView registration() {
        return new ModelAndView("registration");
    }

    @PostMapping("/registration")
    public ModelAndView registerUser(UserForm userForm) {
        ModelAndView model = new ModelAndView();
        if (dgUserService.registerUser(userForm)) {
            model.setViewName("login");
        } else {
            model.setViewName("registration");
            model.addObject("message", "Такой пользователь уже существует!");
        }
        return model;
    }

    @GetMapping("/activate/{code}")
    public ModelAndView activateUser(@PathVariable String code) {
        ModelAndView model = new ModelAndView("login");
        if (dgUserService.activateUser(code)) {
            model.addObject("message", "Активация пользователя успешно завершена!");
        } else {
            model.addObject("message", "Пользователь с таким кодом активации не найден!");
        }
        return model;
    }

}