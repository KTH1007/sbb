package com.example.sbb.answer.service;

import com.example.sbb.answer.domain.Answer;
import com.example.sbb.answer.repository.AnswerRepository;
import com.example.sbb.global.exception.DataNotFoundException;
import com.example.sbb.member.domain.Member;
import com.example.sbb.question.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    @Autowired
    private final AnswerRepository answerRepository;

    public void create(Question question, String content, Member author) {
        Answer answer = Answer.builder()
                .content(content)
                .createDate(LocalDateTime.now())
                .question(question)
                .author(author)
                .build();
        answerRepository.save(answer);
    }

    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(Answer answer, String content) {
        answer.modify(content);
        answerRepository.save(answer);
    }
}
