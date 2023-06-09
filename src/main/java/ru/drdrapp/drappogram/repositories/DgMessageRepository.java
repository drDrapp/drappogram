package ru.drdrapp.drappogram.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.drdrapp.drappogram.models.DgMessage;

import java.util.List;

@Transactional(readOnly = true)
public interface DgMessageRepository extends JpaRepository<DgMessage, Integer> {
    List<DgMessage> findByTag(String tag);
}