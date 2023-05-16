package ru.drdrapp.drappogram.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.drdrapp.drappogram.froms.UserForm;
import ru.drdrapp.drappogram.models.DgUser;
import ru.drdrapp.drappogram.repositories.DgUserRepository;
import ru.drdrapp.drappogram.services.RegistrationServiceImpl;

import java.util.Optional;

@Controller
public class RegistrationController {
    private final DgUserRepository dgUserRepository;
    private final RegistrationServiceImpl registrationService;

    public RegistrationController(DgUserRepository dgUserRepository, RegistrationServiceImpl registrationService) {
        this.dgUserRepository = dgUserRepository;
        this.registrationService = registrationService;
    }

    @GetMapping("/registration")
    public ModelAndView registration() {
        return new ModelAndView("registration");
    }

    @PostMapping("/registration")
    public ModelAndView addUser(UserForm userForm) {
        ModelAndView model;
        Optional<DgUser> dgUserCandidate = dgUserRepository.findOneByLogin(userForm.getLogin());
        if (dgUserCandidate.isPresent()) {
            model = new ModelAndView("registration");
            model.addObject("message", "Такой пользователь существует!");
        } else {
            registrationService.registration(userForm);
            model = new ModelAndView("login");
        }
        return model;
    }

    @GetMapping("/activate/{code}")
    public ModelAndView activate(@PathVariable String code) {
        boolean isActivated = registrationService.activateUser(code);
        ModelAndView model = new ModelAndView("login");
        if (isActivated) {
            model.addObject("message", "Активация пользователя успешно завершена!");
        } else {
            model.addObject("message", "Пользователь с таким кодом активации не найден!");
        }
        return model;
    }

}