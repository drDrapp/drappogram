package ru.drdrapp.drappogram.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.drdrapp.drappogram.models.DgUser;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DgUserRepository extends JpaRepository<DgUser, Long> {
    Optional<DgUser> findByLogin(String login);

    List<DgUser> findByEmail(String login);

    Optional<DgUser> findByActivationCode(String activationCode);

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, value = "dgUser_entity-graph")
    @Query("SELECT u FROM DgUser u WHERE u.id=?1")
    Optional<DgUser> getDgUserWithDgMessages(long id);

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, value = "dgUser_entity-graph")
    @Query("SELECT u FROM DgUser u WHERE ?1 MEMBER of u.subscribers")
    List<DgUser> getSubscriptions(DgUser id);

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, value = "dgUser_entity-graph")
    @Query("SELECT u FROM DgUser u WHERE ?1 MEMBER of u.subscriptions")
    List<DgUser> getSubscribers(DgUser id);
}