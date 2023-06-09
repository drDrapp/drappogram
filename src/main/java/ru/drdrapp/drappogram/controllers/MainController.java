package ru.drdrapp.drappogram.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class MainController {
    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("home");
    }
}