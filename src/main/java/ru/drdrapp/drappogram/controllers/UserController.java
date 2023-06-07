package ru.drdrapp.drappogram.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.drdrapp.drappogram.froms.ProfileForm;
import ru.drdrapp.drappogram.models.DgUser;
import ru.drdrapp.drappogram.models.Role;
import ru.drdrapp.drappogram.services.DgUserService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final DgUserService dgUserService;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ModelAndView getAllUsers() {
        ModelAndView model = new ModelAndView("userList");
        model.addObject("userList", dgUserService.getAllUsers());
        return model;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{dgUser}")
    public ModelAndView editUser(@PathVariable DgUser dgUser) {
        ModelAndView model = new ModelAndView("userEdit");
        List<Role> allRoles = Arrays.asList(Role.values());
        model.addObject("editableUser", dgUser);
        model.addObject("rolesAll", allRoles);
        return model;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ModelAndView updateUser(ProfileForm profileForm,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") DgUser dgUser) {
        dgUser.setFirstName(profileForm.getFirstName());
        dgUser.setLastName(profileForm.getLastName());
        dgUser.setEmail(profileForm.getEmail());
        dgUser.setPassword(passwordEncoder.encode(profileForm.getPassword()));
        Set<String> allRoles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        dgUser.getRoles().clear();
        for (String key : form.keySet()) {
            if (allRoles.contains(key)) {
                dgUser.getRoles().add(Role.valueOf(key));
            }
        }
        dgUserService.saveUser(dgUser);
        return new ModelAndView("redirect:/user");
    }

    @GetMapping("profile")
    public ModelAndView getProfile() {
        return new ModelAndView("profile");
    }

    @PostMapping("profile")
    public ModelAndView UpdateProfile(ProfileForm profileForm,
                                      @RequestParam("userId") DgUser dgUser) {
        dgUserService.updateProfile(profileForm, dgUser);
        return new ModelAndView("redirect:/user/profile");
    }

}