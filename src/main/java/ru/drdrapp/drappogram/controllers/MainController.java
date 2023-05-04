package ru.drdrapp.drappogram.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.drdrapp.drappogram.models.DgMessage;
import ru.drdrapp.drappogram.models.DgUser;
import ru.drdrapp.drappogram.repositories.DgMessageRepository;
import ru.drdrapp.drappogram.repositories.DgUserRepository;

import java.util.Optional;

@Controller
public class MainController {
    private final DgMessageRepository dgMessageRepository;
    private final DgUserRepository dgUserRepository;

    public MainController(DgMessageRepository dgMessageRepository,
                          DgUserRepository dgUserRepository) {
        this.dgMessageRepository = dgMessageRepository;
        this.dgUserRepository = dgUserRepository;
    }
    @GetMapping("/")
    public ModelAndView home(ModelAndView model) {
        return new ModelAndView("home");
    }

    @GetMapping("/main")
    public ModelAndView mainGet(@RequestParam(required = false, defaultValue = "") String tagFilter, ModelAndView model) {
        Iterable<DgMessage> dgMessages;
        if (tagFilter != null && !tagFilter.isEmpty()) {
            dgMessages = dgMessageRepository.findByTag(tagFilter);
        } else {
            dgMessages = dgMessageRepository.findAll();
        }
        model.addObject("dgMessages", dgMessages);
        model.addObject("tagFilter", tagFilter);
        return model;
    }

    @PostMapping("/main")
    public ModelAndView mainPost(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String text, @RequestParam String tag, ModelAndView model) {
        Optional<DgUser> dgUser = dgUserRepository.findOneByLogin(userDetails.getUsername());
        if (dgUser.isPresent()){
            DgMessage dgMessage = new DgMessage(text, tag, dgUser.get());
            dgMessageRepository.save(dgMessage);
            Iterable<DgMessage> dgMessages = dgMessageRepository.findAll();
            model.addObject("dgUser", dgUser);
            model.addObject("dgMessages", dgMessages);
        }
        return model;
    }

}