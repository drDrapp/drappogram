package ru.drdrapp.drappogram.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.drdrapp.drappogram.froms.UserForm;
import ru.drdrapp.drappogram.models.DgUser;
import ru.drdrapp.drappogram.models.Role;
import ru.drdrapp.drappogram.models.State;
import ru.drdrapp.drappogram.repositories.DgUserRepository;

import java.util.Collections;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final DgUserRepository dgUserRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationServiceImpl(DgUserRepository dgUserRepository, PasswordEncoder passwordEncoder) {
        this.dgUserRepository = dgUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registration(UserForm userForm) {
        String hashPassword = passwordEncoder.encode(userForm.getPassword());
        DgUser dgUser = DgUser.builder()
                .login(userForm.getLogin())
                .hashPassword(hashPassword)
                .roles(Collections.singleton(Role.USER))
                .state(State.ACTIVE)
                .build();
        dgUserRepository.save(dgUser);
    }
}
