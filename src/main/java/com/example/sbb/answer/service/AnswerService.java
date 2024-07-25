package com.example.sbb.answer.service;

import com.example.sbb.answer.domain.Answer;
import com.example.sbb.answer.repository.AnswerRepository;
import com.example.sbb.question.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {

    @Autowired
    private final AnswerRepository answerRepository;

    public void create(Question question, String content) {
        Answer answer = Answer.builder()
                .content(content)
                .createDate(LocalDateTime.now())
                .question(question)
                .build();
        answerRepository.save(answer);
    }
}
