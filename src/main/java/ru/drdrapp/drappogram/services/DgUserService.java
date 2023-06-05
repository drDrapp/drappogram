package ru.drdrapp.drappogram.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.drdrapp.drappogram.froms.ProfileForm;
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

    public Boolean registerUser(DgUser dgUser) {
        Optional<DgUser> dgUserCandidate = dgUserRepository.findByLogin(dgUser.getLogin());
        List<DgUser> dgUserCandidates = dgUserRepository.findByEmail(dgUser.getEmail());
        if (dgUserCandidate.isPresent() || (!dgUserCandidates.isEmpty())) {
            return false;
        } else {
            dgUser.setPassword(passwordEncoder.encode(dgUser.getPassword()));
            dgUser.setRoles(Collections.singleton(Role.USER));
            dgUser.setState(State.ACTIVE);
            dgUser.setActivationCode(UUID.randomUUID().toString());
            dgUser.setActive(false);
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

    public void updateProfile(ProfileForm profileForm, DgUser dgUser) {
        boolean isProfileChanged = false;

        String dgUserFirstName = dgUser.getFirstName();
        String profileFormFirstName = profileForm.getFirstName();
        if ((profileFormFirstName != null && !profileFormFirstName.equals(dgUserFirstName)) ||
                (dgUserFirstName != null && !dgUserFirstName.equals(profileFormFirstName))) {
            dgUser.setFirstName(profileFormFirstName);
            isProfileChanged = true;
        }

        String profileFormLastName = profileForm.getLastName();
        String dgUserLastName = dgUser.getLastName();
        if ((profileFormLastName != null && !profileFormLastName.equals(dgUserLastName)) ||
                (dgUserLastName != null && !dgUserLastName.equals(profileFormLastName))) {
            dgUser.setLastName(profileFormLastName);
            isProfileChanged = true;
        }

        String userFormPassword = profileForm.getPassword();
        if (hasLength(userFormPassword)) {
            dgUser.setPassword(passwordEncoder.encode(userFormPassword));
            isProfileChanged = true;
        }

        String profileFormEmail = profileForm.getEmail();
        String dgUserEmail = dgUser.getEmail();
        if ((profileFormEmail != null && !profileFormEmail.equals(dgUserEmail)) ||
                (dgUserEmail != null && !dgUserEmail.equals(profileFormEmail))) {
            dgUser.setEmail(profileFormEmail);
            if (hasLength(profileFormEmail)) {
                dgUser.setActivationCode(UUID.randomUUID().toString());
                SendActivationCode(dgUser);
            }
            isProfileChanged = true;
        }
        if (isProfileChanged) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Authentication newAuth = new UsernamePasswordAuthenticationToken(new DgUserDetails(dgUser), auth.getCredentials(), auth.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(newAuth);
            saveUser(dgUser);
        }
    }
}