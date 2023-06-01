package ru.drdrapp.drappogram.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.drdrapp.drappogram.froms.UserForm;
import ru.drdrapp.drappogram.models.DgUser;
import ru.drdrapp.drappogram.models.Role;
import ru.drdrapp.drappogram.models.State;
import ru.drdrapp.drappogram.repositories.DgUserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.util.StringUtils.hasLength;

@Service
@RequiredArgsConstructor
public class DgUserService implements UserDetailsService {

    private final DgUserRepository dgUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Override
    public DgUserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return new DgUserDetails(dgUserRepository.findByLogin(login).orElseThrow(IllegalArgumentException::new));
    }

    public Boolean registerUser(UserForm userForm) {
        Optional<DgUser> dgUserCandidate = dgUserRepository.findByLogin(userForm.getLogin());
        if (dgUserCandidate.isPresent()) {
            return false;
        } else {
            String hashPassword = passwordEncoder.encode(userForm.getPassword());
            DgUser dgUser = DgUser.builder()
                    .login(userForm.getLogin())
                    .hashPassword(hashPassword)
                    .roles(Collections.singleton(Role.USER))
                    .state(State.ACTIVE)
                    .email(userForm.getEmail())
                    .activationCode(UUID.randomUUID().toString())
                    .active(false)
                    .build();
            dgUserRepository.save(dgUser);
            if (!dgUser.getEmail().isEmpty()) {
                SendActivationCode(dgUser);
            }
            return true;
        }
    }

    private void SendActivationCode(DgUser dgUser) {
        String message = String.format(
                """
                        Привет, %s!\s
                        Добро пожаловать в drappogram!\s
                        Ссылка для активации: http://localhost:8080/activate/%s""",
                dgUser.getLogin(),
                dgUser.getActivationCode()
        );
        mailService.sendMail(dgUser.getEmail(), "Код активации для drappogram", message);
    }

    public boolean activateUser(String code) {
        Optional<DgUser> dgUserCandidate = dgUserRepository.findByActivationCode(code);
        if (dgUserCandidate.isEmpty()) {
            return false;
        } else {
            DgUser dgUser = dgUserCandidate.get();
            dgUser.setActivationCode(null);
            dgUserRepository.save(dgUser);
            return true;
        }
    }

    public List<DgUser> getAllUsers() {
        return dgUserRepository.findAll();
    }

    public void saveUser(DgUser dgUser) {
        dgUserRepository.save(dgUser);
    }

    public void updateProfile(UserForm userForm, DgUser dgUser) {

        if (isEmailChanged(userForm, dgUser) || isPasswordChanged(userForm, dgUser)) {
            saveUser(dgUser);
        }
    }

    private boolean isPasswordChanged(UserForm userForm, DgUser dgUser) {
        String userFormPassword = userForm.getPassword();
        if (hasLength(userFormPassword)) {
            dgUser.setHashPassword(passwordEncoder.encode(userFormPassword));
            return true;
        }
        return false;
    }

    private boolean isEmailChanged(UserForm userForm, DgUser dgUser) {
        String userFormEmail = userForm.getEmail();
        String dgUserEmail = dgUser.getEmail();
        if ((userFormEmail != null && !userFormEmail.equals(dgUserEmail)) ||
                (dgUserEmail != null && !dgUserEmail.equals(userFormEmail))) {
            dgUser.setEmail(userFormEmail);
            if (hasLength(userFormEmail)) {
                dgUser.setActivationCode(UUID.randomUUID().toString());
                SendActivationCode(dgUser);
            }
            return true;
        }
        return false;
    }
}