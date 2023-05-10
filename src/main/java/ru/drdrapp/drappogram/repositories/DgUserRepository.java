package ru.drdrapp.drappogram.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.drdrapp.drappogram.models.DgUser;

import java.util.List;
import java.util.Optional;

public interface DgUserRepository extends JpaRepository<DgUser, Long> {
    List<DgUser> findAllByFirstName(String firstName);
    Optional<DgUser> findOneByLogin(String login);
    DgUser findByActivationCode(String activationCode);
}