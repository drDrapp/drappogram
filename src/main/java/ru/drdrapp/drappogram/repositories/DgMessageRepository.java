package ru.drdrapp.drappogram.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.drdrapp.drappogram.models.DgMessage;

import java.util.List;

public interface DgMessageRepository extends JpaRepository<DgMessage, Integer> {
    List<DgMessage> findByTag(String tag);
}