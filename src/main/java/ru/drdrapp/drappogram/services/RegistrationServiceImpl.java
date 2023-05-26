package ru.drdrapp.drappogram.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.drdrapp.drappogram.froms.UserForm;
import ru.drdrapp.drappogram.models.DgUser;
import ru.drdrapp.drappogram.models.Role;
import ru.drdrapp.drappogram.models.State;
import ru.drdrapp.drappogram.repositories.DgUserRepository;
import ru.drdrapp.drappogram.services.interfaces.RegistrationService;

import java.util.Collections;
import java.util.UUID;
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final DgUserRepository dgUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSender mailSender;

    public RegistrationServiceImpl(PasswordEncoder passwordEncoder, MailSender mailSender, DgUserRepository dgUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.dgUserRepository = dgUserRepository;
    }

    public boolean activateUser(String code) {
        DgUser dgUser = dgUserRepository.findByActivationCode(code);
        if (dgUser == null) {
            return false;
        }
        dgUser.setActivationCode(null);
        dgUserRepository.save(dgUser);
        return true;
    }

    @Override
    public void registration(UserForm userForm) {
        String hashPassword = passwordEncoder.encode(userForm.getPassword());
        DgUser dgUser = DgUser.builder()
                .login(userForm.getLogin())
                .hashPassword(hashPassword)
                .roles(Collections.singleton(Role.USER))
                .state(State.ACTIVE)
                .email(userForm.getEmail())
                .activationCode(UUID.randomUUID().toString())
                .build();
        dgUserRepository.save(dgUser);
        if (!dgUser.getEmail().isEmpty()) {
            String message = String.format(
                    """
                            Привет, %s!\s
                            Добро пожаловать в drappogram!\s
                            Ссылка для активации: http://localhost:8080/activate/%s""",
                    dgUser.getLogin(),
                    dgUser.getActivationCode()
            );
            mailSender.sendMail(dgUser.getEmail(), "Код активации для drappogram", message);
        }
    }
}
