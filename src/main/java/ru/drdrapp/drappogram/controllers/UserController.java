package ru.drdrapp.drappogram.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.drdrapp.drappogram.models.DgUser;
import ru.drdrapp.drappogram.models.Role;
import ru.drdrapp.drappogram.repositories.DgUserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final DgUserRepository dgUserRepository;

    @GetMapping
    public ModelAndView userList() {
        ModelAndView model = new ModelAndView("userList");
        model.addObject("userList", dgUserRepository.findAll());
        return model;
    }

    @GetMapping("{dgUser}")
    public ModelAndView userEdit(@PathVariable DgUser dgUser) {
        ModelAndView model = new ModelAndView("userEdit");
        List<Role> allRoles = Arrays.asList(Role.values());
        model.addObject("editableUser", dgUser);
        model.addObject("rolesAll", allRoles);
        return model;
    }

    @PostMapping
    public ModelAndView userSave(@RequestParam String firstName,
                                 @RequestParam String lastName,
                                 @RequestParam Map<String, String> form,
                                 @RequestParam("userId") DgUser dgUser) {
        dgUser.setFirstName(firstName);
        dgUser.setLastName(lastName);
        Set<String> allRoles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        dgUser.getRoles().clear();
        for (String key : form.keySet()) {
            if (allRoles.contains(key)) {
                dgUser.getRoles().add(Role.valueOf(key));
            }
        }
        dgUserRepository.save(dgUser);
        return new ModelAndView("redirect:/user");
    }

}