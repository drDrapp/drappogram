package ru.drdrapp.drappogram.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.drdrapp.drappogram.Utils.DgUtils;
import ru.drdrapp.drappogram.models.DgMessage;
import ru.drdrapp.drappogram.models.DgUser;
import ru.drdrapp.drappogram.repositories.DgMessageRepository;
import ru.drdrapp.drappogram.repositories.DgUserRepository;
import ru.drdrapp.drappogram.services.DgUserDetails;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final DgMessageRepository dgMessageRepository;

    @Value("${upload.path}")
    private String uploadPath;
    private final DgUserRepository dgUserRepository;

    @GetMapping()
    public ModelAndView getAllMessages(@RequestParam(required = false, defaultValue = "") String tagFilter) {
        ModelAndView model = new ModelAndView("messages");
        List<DgMessage> dgMessages;
        if (tagFilter != null && !tagFilter.isEmpty()) {
            dgMessages = dgMessageRepository.findByTag(tagFilter);
        } else {
            dgMessages = dgMessageRepository.findAll();
        }
        model.addObject("dgMessages", dgMessages);
        model.addObject("tagFilter", tagFilter);
        return model;
    }

    @PostMapping()
    public ModelAndView postMessage(@AuthenticationPrincipal DgUserDetails dgUserDetails,
                                    @Valid DgMessage dgMessage,
                                    BindingResult bindingResult,
                                    @RequestParam("messageFile") MultipartFile messageFile) throws IOException {
        ModelAndView model = new ModelAndView("messages");
        dgMessage.setAuthor(dgUserDetails.getDgUser());
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = DgUtils.getErrors(bindingResult);
            model.addObject("errorsMap", errorsMap);
            model.addObject("dgMessage", dgMessage);
        } else {
            saveMessageFile(dgMessage, messageFile);
            model.addObject("dgMessage", null);
            dgMessageRepository.save(dgMessage);
        }
        List<DgMessage> dgMessages;
        dgMessages = dgMessageRepository.findAll();
        model.addObject("dgMessages", dgMessages);
        return model;
    }

    private void saveMessageFile(@Valid DgMessage dgMessage, @RequestParam MultipartFile messageFile) throws IOException {
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
    }

    @GetMapping("/user/{dgUser}")
    public ModelAndView userMessages(@AuthenticationPrincipal DgUserDetails dgUserDetails,
                                     @PathVariable DgUser dgUser,
                                     @RequestParam(required = false) DgMessage message
    ) {
        ModelAndView model = new ModelAndView("userMessages");
        Optional<DgUser> dgUserWithMessagesMessages = dgUserRepository.getDgUserWithDgMessages(dgUser.getId());
        model.addObject("dgMessages", dgUserWithMessagesMessages.get().getDgMessages());
        model.addObject("dgMessage", message);
        model.addObject("isCurrentUser", dgUserDetails.getDgUser().equals(dgUser));
        return model;
    }

    @PostMapping("/user/{dgUser}")
    public String updateMessage(@AuthenticationPrincipal DgUserDetails dgUserDetails,
                                @PathVariable Long dgUser,
                                @RequestParam("message") DgMessage message,
                                @RequestParam("text") String text,
                                @RequestParam("tag") String tag,
                                @RequestParam("messageFile") MultipartFile messageFile
    ) throws IOException {
        if (message.getAuthor().equals(dgUserDetails.getDgUser())) {
            if (StringUtils.hasLength(text)) {
                message.setText(text);
            }
            if (StringUtils.hasLength(tag)) {
                message.setTag(tag);
            }
            saveMessageFile(message, messageFile);
            dgMessageRepository.save(message);
        }

        return "redirect:/messages/user/" + dgUser;
    }

}