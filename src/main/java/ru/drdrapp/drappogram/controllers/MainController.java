package ru.drdrapp.drappogram.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.drdrapp.drappogram.Utils.DgUtils;
import ru.drdrapp.drappogram.models.DgMessage;
import ru.drdrapp.drappogram.repositories.DgMessageRepository;
import ru.drdrapp.drappogram.services.DgUserDetails;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final DgMessageRepository dgMessageRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    @GetMapping("/main")
    public ModelAndView getAllMessages(@RequestParam(required = false, defaultValue = "") String tagFilter) {
        ModelAndView model = new ModelAndView("main");
        List<DgMessage> dgMessages;
        if (tagFilter != null && !tagFilter.isEmpty()) {
            dgMessages = dgMessageRepository.findByTag(tagFilter);
        } else {
            dgMessages = dgMessageRepository.findAll();
        }
        model.addObject("allMessages", dgMessages);
        model.addObject("tagFilter", tagFilter);
        return model;
    }

    @PostMapping("/main")
    public ModelAndView postMessage(@AuthenticationPrincipal DgUserDetails dgUserDetails,
                                    @Valid DgMessage dgMessage,
                                    BindingResult bindingResult,
                                    @RequestParam("messageFile") MultipartFile messageFile) throws IOException {
        ModelAndView model = new ModelAndView("main");
        dgMessage.setAuthor(dgUserDetails.getDgUser());
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = DgUtils.getErrors(bindingResult);
            model.addObject("errorsMap", errorsMap);
            model.addObject("dgMessage", dgMessage);
        } else {
            if (messageFile != null && !Objects.requireNonNull(messageFile.getOriginalFilename()).isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    boolean wasFileSystemOperationResult = uploadDir.mkdir();
                    if (!wasFileSystemOperationResult) {
                        System.out.println("Ошибка создания директории!");
                    }
                }
                String resultFilename = UUID.randomUUID() + "." + messageFile.getOriginalFilename();
                messageFile.transferTo(new File(uploadPath + "/" + resultFilename));
                dgMessage.setFilename(resultFilename);
            }
            dgMessageRepository.save(dgMessage);
        }
        List<DgMessage> dgMessages;
        dgMessages = dgMessageRepository.findAll();
        model.addObject("allMessages", dgMessages);
        return model;
    }

}