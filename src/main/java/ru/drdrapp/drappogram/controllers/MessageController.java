package ru.drdrapp.drappogram.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.drdrapp.drappogram.Utils.DgUtils;
import ru.drdrapp.drappogram.models.DgMessage;
import ru.drdrapp.drappogram.models.DgUser;
import ru.drdrapp.drappogram.services.DgMessageService;
import ru.drdrapp.drappogram.services.DgUserDetails;
import ru.drdrapp.drappogram.services.DgUserService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final DgMessageService dgMessageService;
    private final DgUserService dgUserService;

    @GetMapping()
    public ModelAndView getAllMessages(@RequestParam(required = false, defaultValue = "") String tagFilter,
                                       @RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "6") int size){
        ModelAndView model = new ModelAndView("messages");

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<DgMessage> dgMessages = dgMessageService.getDgMessagesByFilter(tagFilter, pageable);

        model.addObject("dgMessages", dgMessages.getContent());
        model.addObject("tagFilter", tagFilter);

        model.addObject("currentPage", dgMessages.getNumber() + 1);
        model.addObject("totalItems", dgMessages.getTotalElements());
        model.addObject("totalPages", dgMessages.getTotalPages());
        model.addObject("pageSize", size);

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
            dgMessageService.saveDgMessageFile(dgMessage, messageFile);
            model.addObject("dgMessage", null);
            dgMessageService.saveDgMessage(dgMessage);
        }
        List<DgMessage> dgMessages = dgMessageService.getListDgMessage();
        model.addObject("dgMessages", dgMessages);
        return model;
    }

    @GetMapping("/user/{dgUser}")
    public ModelAndView userMessages(@AuthenticationPrincipal DgUserDetails dgUserDetails,
                                     @PathVariable DgUser dgUser,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "6") int size,
                                     @RequestParam(required = false) DgMessage message
    ) {
        ModelAndView model = new ModelAndView("userMessages");
        model.addObject("viewingDgUser", dgUser);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<DgMessage> dgMessages = dgMessageService.getDgMessagesByAuthorId(dgUser.getId(), "", pageable);
        model.addObject("dgMessages", dgMessages.getContent());
        model.addObject("currentPage", dgMessages.getNumber() + 1);
        model.addObject("totalItems", dgMessages.getTotalElements());
        model.addObject("totalPages", dgMessages.getTotalPages());
        model.addObject("pageSize", size);

        model.addObject("subscriptionsCount", dgUserService.getSubscriptions(dgUser).size());
        model.addObject("subscribersCount", dgUserService.getSubscribers(dgUser).size());
        model.addObject("isSubscriber", dgUserService.getSubscribers(dgUser).contains(dgUserDetails.getDgUser()));
        model.addObject("dgMessage", message);
        model.addObject("isCurrentUser", dgUserDetails.getDgUser().equals(dgUser));
        model.addObject("showEditor", dgUserDetails.getDgUser().equals(dgUser) && (message != null));

        return model;
    }

    @PostMapping("/user/{userId}")
    public ModelAndView updateMessage(@AuthenticationPrincipal DgUserDetails dgUserDetails,
                                      @PathVariable Long userId,
                                      @RequestParam("id") DgMessage dgMessage,
                                      @RequestParam("text") String text,
                                      @RequestParam("tag") String tag,
                                      @RequestParam("messageFile") MultipartFile messageFile
    ) throws IOException {
        dgMessageService.updateDgMessage(dgUserDetails, dgMessage, text, tag, messageFile);
        return new ModelAndView("redirect:/messages/user/" + userId);
    }

}