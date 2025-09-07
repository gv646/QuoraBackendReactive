package com.example.demo.repositories;

import com.example.demo.models.Answer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AnswerRepository extends ReactiveMongoRepository<Answer, String> {
    
    Flux<Answer> findByQuestionId(String questionId, Pageable pageable);
    
    @Query("{'content': {$regex: ?0, $options: 'i'}}")
    Flux<Answer> findByContentContainingIgnoreCase(String query, Pageable pageable);
    
    Flux<Answer> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
