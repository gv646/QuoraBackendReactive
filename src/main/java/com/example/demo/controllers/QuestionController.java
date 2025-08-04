package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @GetMapping("/author/{authorId}")
    public Flux<QuestionResponse> getQuestionsByAuthorId(@PathVariable String authorId) {
        return null;
    }
}
