package com.example.backend_sistemas_distribuidos.persistance;

import com.example.backend_sistemas_distribuidos.business.entities.Alumno;
import com.example.backend_sistemas_distribuidos.business.entities.ChatMessage;
import com.example.backend_sistemas_distribuidos.business.entities.Conversation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;


@Repository
public interface ConversationRepository extends CrudRepository<Conversation, Long> {

    Optional<Conversation> findByParticipantOneIdAndParticipantTwoId(Long participantOneId, Long participantTwoId);

    Optional<Conversation> findByParticipantTwoIdAndParticipantOneId(Long participantTwoId, Long participantOneId);

    Optional<Conversation> findByParticipantOneAndParticipantTwo(Alumno participantOne, Alumno participantTwo);


    List<Conversation> findByParticipantOneIdOrParticipantTwoId(Long participantOneId, Long participantTwoId);

}

