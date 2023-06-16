package ru.drdrapp.drappogram.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.drdrapp.drappogram.models.DgMessage;

@Transactional(readOnly = true)
public interface DgMessageRepository extends JpaRepository<DgMessage, Integer> {
    Page<DgMessage> findAll(Pageable pageable);
    Page<DgMessage> findByTag(String tag, Pageable pageable);
    Page<DgMessage> findByAuthor_Id(long authorId, Pageable pageable);
    Page<DgMessage> findByAuthor_IdAndTag(long authorId, String tag, Pageable pageable);
}