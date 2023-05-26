package ru.drdrapp.drappogram.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.drdrapp.drappogram.froms.UserForm;
import ru.drdrapp.drappogram.models.DgUser;
import ru.drdrapp.drappogram.repositories.DgUserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DgUserServiceImpl implements UserDetailsService {

    private final RegistrationServiceImpl registrationService;
    private final DgUserRepository dgUserRepository;

    @Override
    public DgUserDetailsImpl loadUserByUsername(String login) throws UsernameNotFoundException {
        return new DgUserDetailsImpl(dgUserRepository.findOneByLogin(login).orElseThrow(IllegalArgumentException::new));
    }

    public Boolean registerUser(UserForm userForm) {
        Optional<DgUser> dgUserCandidate = dgUserRepository.findOneByLogin(userForm.getLogin());
        if (dgUserCandidate.isPresent()) {
            return false;
        } else {
            registrationService.registration(userForm);
            return true;
        }
    }

    public Boolean activateUser(String code) {
        return registrationService.activateUser(code);
    }

}