package ru.drdrapp.drappogram.tmp;

import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.drdrapp.drappogram.models.DgMessage;
import ru.drdrapp.drappogram.models.DgUser;
import ru.drdrapp.drappogram.models.Role;
import ru.drdrapp.drappogram.models.State;
import ru.drdrapp.drappogram.repositories.DgMessageRepository;

import java.util.Collections;

@Controller
public class TmpController {

    private final ApplicationContext context;
    private final DgMessageRepository dgMessageRepository;
    private final TmpClass tmpClass;

    public TmpController(DgMessageRepository dgMessageRepository, TmpClass tmpClass, ApplicationContext context) {
        this.dgMessageRepository = dgMessageRepository;
        this.tmpClass = tmpClass;
        this.context = context;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/tmp")
    public ModelAndView getLoginPage() {
        ModelAndView model = new ModelAndView("tmp");
        DgUser dgUser = new DgUser(777L,
                "xName1",
                "xName2",
                "xLogin",
                "psw",
                Collections.singleton(Role.USER),
                null,
                State.ACTIVE,
                "ussr@mail.ru",
                "activationCode",
                true,
                null,
                null);
        model.addObject("testObject", dgUser);
        model.addObject("tmpObject", tmpClass);
        TmpClass myTmpBean = context.getBean(TmpClass.class);
        myTmpBean.setTmpData("tempDataText");
        Iterable<DgMessage> dgMessages = dgMessageRepository.findAll();
        model.addObject("allMessages", dgMessages);
        return model;
    }
}