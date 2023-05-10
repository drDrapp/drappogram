package ru.drdrapp.drappogram.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.drdrapp.drappogram.models.DgMessage;
import ru.drdrapp.drappogram.models.DgUser;
import ru.drdrapp.drappogram.repositories.DgMessageRepository;
import ru.drdrapp.drappogram.repositories.DgUserRepository;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Controller
public class MainController {
    private final DgMessageRepository dgMessageRepository;
    private final DgUserRepository dgUserRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public MainController(DgMessageRepository dgMessageRepository,
                          DgUserRepository dgUserRepository) {
        this.dgMessageRepository = dgMessageRepository;
        this.dgUserRepository = dgUserRepository;
    }
    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    @GetMapping("/main")
    public ModelAndView mainGet(@RequestParam(required = false, defaultValue = "") String tagFilter,
                                ModelAndView model) {
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
    public ModelAndView mainPost(@RequestParam("messageText") String messageText,
                                 @RequestParam("messageTag") String messageTag,
                                 @RequestParam("messageFile") MultipartFile messageFile,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 ModelAndView model) throws IOException {
        Optional<DgUser> dgUser = dgUserRepository.findOneByLogin(userDetails.getUsername());
        if (dgUser.isPresent()){
            DgMessage dgMessage = new DgMessage(messageText, messageTag, dgUser.get());
            if (messageFile != null && !Objects.requireNonNull(messageFile.getOriginalFilename()).isEmpty()){
                    File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()){
                    uploadDir.mkdir();
                }
                String resultFilename = UUID.randomUUID().toString() + "." + messageFile.getOriginalFilename();
                messageFile.transferTo(new File(uploadPath + "/" + resultFilename));
                dgMessage.setFilename(resultFilename);
            }
            dgMessageRepository.save(dgMessage);
            Iterable<DgMessage> dgMessages = dgMessageRepository.findAll();
            model.addObject("dgUser", dgUser);
            model.addObject("dgMessages", dgMessages);
        }
        return model;
    }

}