package com.example.demo.services;

import com.example.demo.dto.AnswerRequestDTO;
import com.example.demo.dto.AnswerResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAnswerService {

    Mono<AnswerResponseDTO> createAnswer(AnswerRequestDTO answerRequestDTO);

    Mono<AnswerResponseDTO> getAnswerById(String id);

    Flux<AnswerResponseDTO> getAllAnswers(String cursor, int size);

    Flux<AnswerResponseDTO> getAnswersByQuestionId(String questionId, String cursor, int size);

    Mono<AnswerResponseDTO> updateAnswer(String id, AnswerRequestDTO answerRequestDTO);

    Mono<Void> deleteAnswer(String id);

    Flux<AnswerResponseDTO> searchAnswers(String query, int page, int size);
}
