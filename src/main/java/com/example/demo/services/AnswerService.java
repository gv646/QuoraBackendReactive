package com.example.demo.services;

import com.example.demo.dto.AnswerRequestDTO;
import com.example.demo.dto.AnswerResponseDTO;
import com.example.demo.models.Answer;
import com.example.demo.repositories.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AnswerService implements IAnswerService {
    
    private final AnswerRepository answerRepository;

    @Override
    public Mono<AnswerResponseDTO> createAnswer(AnswerRequestDTO answerRequestDTO) {
        Answer answer = Answer.builder()
                .content(answerRequestDTO.getContent())
                .questionId(answerRequestDTO.getQuestionId())
                .build();
        
        return answerRepository.save(answer)
                .map(this::mapToResponseDTO);
    }

    @Override
    public Mono<AnswerResponseDTO> getAnswerById(String id) {
        return answerRepository.findById(id)
                .map(this::mapToResponseDTO)
                .switchIfEmpty(Mono.error(new RuntimeException("Answer not found with id: " + id)));
    }

    @Override
    public Flux<AnswerResponseDTO> getAllAnswers(String cursor, int size) {
        Pageable pageable = PageRequest.of(0, size);
        return answerRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(this::mapToResponseDTO);
    }

    @Override
    public Flux<AnswerResponseDTO> getAnswersByQuestionId(String questionId, String cursor, int size) {
        Pageable pageable = PageRequest.of(0, size);
        return answerRepository.findByQuestionId(questionId, pageable)
                .map(this::mapToResponseDTO);
    }

    @Override
    public Mono<AnswerResponseDTO> updateAnswer(String id, AnswerRequestDTO answerRequestDTO) {
        return answerRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Answer not found with id: " + id)))
                .flatMap(existingAnswer -> {
                    existingAnswer.setContent(answerRequestDTO.getContent());
                    existingAnswer.setQuestionId(answerRequestDTO.getQuestionId());
                    return answerRepository.save(existingAnswer);
                })
                .map(this::mapToResponseDTO);
    }

    @Override
    public Mono<Void> deleteAnswer(String id) {
        return answerRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Answer not found with id: " + id)))
                .flatMap(answerRepository::delete)
                .then();
    }

    @Override
    public Flux<AnswerResponseDTO> searchAnswers(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return answerRepository.findByContentContainingIgnoreCase(query, pageable)
                .map(this::mapToResponseDTO);
    }

    private AnswerResponseDTO mapToResponseDTO(Answer answer) {
        return AnswerResponseDTO.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .questionId(answer.getQuestionId())
                .createdAt(answer.getCreatedAt())
                .updatedAt(answer.getUpdatedAt())
                .build();
    }
}
