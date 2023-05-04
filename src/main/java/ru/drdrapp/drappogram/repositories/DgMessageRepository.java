package ru.drdrapp.drappogram.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.drdrapp.drappogram.models.DgMessage;

import java.util.List;

public interface DgMessageRepository extends CrudRepository<DgMessage, Integer> {
    List<DgMessage> findByTag(String tag);
}