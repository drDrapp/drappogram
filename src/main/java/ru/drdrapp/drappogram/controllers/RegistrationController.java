package ru.drdrapp.drappogram.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ModelAndView registration(ModelAndView model) {
        return model;
    }

    @PostMapping("/registration")
    public ModelAndView addUser(UserForm userForm, ModelAndView model) {
        Optional<DgUser> dgUserCandidate = dgUserRepository.findOneByLogin(userForm.getLogin());
        if (dgUserCandidate.isPresent()) {
            model.addObject("message", "User exists!");
            return model;
        }
        registrationService.registration(userForm);
        return new ModelAndView("/login");
    }

}