package com.example.demo.controllers;

import com.example.demo.dto.AnswerRequestDTO;
import com.example.demo.dto.AnswerResponseDTO;
import com.example.demo.services.IAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    private final IAnswerService answerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AnswerResponseDTO> createAnswer(@RequestBody AnswerRequestDTO answerRequestDTO) {
        return answerService.createAnswer(answerRequestDTO)
                .doOnSuccess(response -> System.out.println("Answer created successfully: " + response))
                .doOnError(error -> System.out.println("Answer creation failed: " + error.getMessage()));
    }

    @GetMapping("/{id}")
    public Mono<AnswerResponseDTO> getAnswerById(@PathVariable String id) {
        return answerService.getAnswerById(id)
                .doOnError(error -> System.out.println("Error fetching answer: " + error))
                .doOnSuccess(response -> System.out.println("Answer fetched successfully: " + response));
    }

    @GetMapping
    public Flux<AnswerResponseDTO> getAllAnswers(
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "10") int size
    ) {
        return answerService.getAllAnswers(cursor, size)
                .doOnError(error -> System.out.println("Error fetching answers: " + error))
                .doOnComplete(() -> System.out.println("Answers fetched successfully"));
    }

    @GetMapping("/question/{questionId}")
    public Flux<AnswerResponseDTO> getAnswersByQuestionId(
            @PathVariable String questionId,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "10") int size
    ) {
        return answerService.getAnswersByQuestionId(questionId, cursor, size)
                .doOnError(error -> System.out.println("Error fetching answers for question: " + error))
                .doOnComplete(() -> System.out.println("Answers for question fetched successfully"));
    }

    @PutMapping("/{id}")
    public Mono<AnswerResponseDTO> updateAnswer(
            @PathVariable String id,
            @RequestBody AnswerRequestDTO answerRequestDTO
    ) {
        return answerService.updateAnswer(id, answerRequestDTO)
                .doOnSuccess(response -> System.out.println("Answer updated successfully: " + response))
                .doOnError(error -> System.out.println("Answer update failed: " + error.getMessage()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAnswer(@PathVariable String id) {
        return answerService.deleteAnswer(id)
                .doOnSuccess(response -> System.out.println("Answer deleted successfully"))
                .doOnError(error -> System.out.println("Answer deletion failed: " + error.getMessage()));
    }

    @GetMapping("/search")
    public Flux<AnswerResponseDTO> searchAnswers(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return answerService.searchAnswers(query, page, size)
                .doOnError(error -> System.out.println("Error searching answers: " + error))
                .doOnComplete(() -> System.out.println("Answer search completed"));
    }
}
