package ru.drdrapp.drappogram.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.drdrapp.drappogram.models.DgUser;

import java.util.Optional;

public interface DgUserRepository extends JpaRepository<DgUser, Long> {
    Optional<DgUser> findByLogin(String login);
    Optional<DgUser> findByActivationCode(String activationCode);
}