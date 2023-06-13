package ru.drdrapp.drappogram.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.drdrapp.drappogram.froms.ProfileForm;
import ru.drdrapp.drappogram.models.DgUser;
import ru.drdrapp.drappogram.models.Role;
import ru.drdrapp.drappogram.services.DgUserDetails;
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{dgUser}")
    public ModelAndView editUser(@PathVariable DgUser dgUser) {
        ModelAndView model = new ModelAndView("userEdit");
        List<Role> allRoles = Arrays.asList(Role.values());
        model.addObject("editableUser", dgUser);
        model.addObject("rolesAll", allRoles);
        return model;
    }

    @GetMapping("/profile")
    public ModelAndView getProfile() {
        return new ModelAndView("profile");
    }

    @PostMapping("/profile")
    public ModelAndView UpdateProfile(ProfileForm profileForm,
                                      @RequestParam("userId") DgUser dgUser) {
        dgUserService.updateProfile(profileForm, dgUser);
        return new ModelAndView("redirect:/user/profile");
    }

    @GetMapping("subscribeTo/{dgUser}")
    public String subscribe(
            @AuthenticationPrincipal DgUserDetails dgUserDetails,
            @PathVariable DgUser dgUser
    ) {
        dgUserService.subscribe(dgUserDetails.getDgUser(), dgUser);
        return "redirect:/messages/user/" + dgUser.getId();
    }

    @GetMapping("unsubscribeFrom/{dgUser}")
    public String unsubscribe(
            @AuthenticationPrincipal DgUserDetails dgUserDetails,
            @PathVariable DgUser dgUser
    ) {
        dgUserService.unsubscribe(dgUserDetails.getDgUser(), dgUser);
        return "redirect:/messages/user/" + dgUser.getId();
    }

    @GetMapping("/{dgUser}/{type}/list")
    public ModelAndView userList(
            @PathVariable DgUser dgUser,
            @PathVariable String type
    ) {
        ModelAndView model = new ModelAndView("subList");
        model.addObject("viewingDgUser", dgUser);
        model.addObject("type", type);
        if ("subscriptions".equals(type)) {
            model.addObject("subList", dgUserService.getSubscriptions(dgUser));
        } else {
            model.addObject("subList", dgUserService.getSubscribers(dgUser));
        }
        return model;
    }

}