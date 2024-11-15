package com.example.backend_sistemas_distribuidos.persistance;

import com.example.backend_sistemas_distribuidos.business.entities.Alumno;
import com.example.backend_sistemas_distribuidos.business.entities.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("SELECT c FROM Conversation c WHERE " +
            "(c.participantOne = :participantOne AND c.participantTwo = :participantTwo) " +
            "OR (c.participantOne = :participantTwo AND c.participantTwo = :participantOne)")
    Optional<Conversation> findByParticipants(@Param("participantOne") Alumno participantOne,
                                              @Param("participantTwo") Alumno participantTwo);

    List<Conversation> findByParticipantOneIdOrParticipantTwoId(Long participantOneId, Long participantTwoId);
}



