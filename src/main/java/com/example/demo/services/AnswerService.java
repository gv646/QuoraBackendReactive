package com.example.demo.services;

import com.example.demo.dto.AnswerRequestDTO;
import com.example.demo.dto.AnswerResponseDTO;
import reactor.core.publisher.Mono;

public class AnswerService implements IAnswerService{
    @Override
    public Mono<AnswerResponseDTO> createAnswer(AnswerRequestDTO answerRequestDTO) {
        return null;
    }

    @Override
    public Mono<AnswerResponseDTO> getAnswerById(String id) {
        return null;
    }
}
