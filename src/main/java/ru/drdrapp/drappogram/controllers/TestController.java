package ru.drdrapp.drappogram.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.drdrapp.drappogram.models.DgMessage;
import ru.drdrapp.drappogram.models.DgUser;
import ru.drdrapp.drappogram.models.State;
import ru.drdrapp.drappogram.repositories.DgMessageRepository;

@Controller
public class TestController {

    private final DgMessageRepository dgMessageRepository;

    public TestController(DgMessageRepository dgMessageRepository) {
        this.dgMessageRepository = dgMessageRepository;
    }

    @GetMapping("/test")
    public ModelAndView getLoginPage() {
        ModelAndView model = new ModelAndView("test");
        DgUser dgUser = new DgUser(777L, "xName", "x2Name", "xLogin", "asd", null, State.ACTIVE, "@mail", "" );
        //model.addObject("testObject", "Test string");
        model.addObject("testObject", dgUser);
        Iterable<DgMessage> dgMessages = dgMessageRepository.findAll();
        model.addObject("allMessages", dgMessages);
        return model;
    }
}