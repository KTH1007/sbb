package com.example.sbb.question.service;

import com.example.sbb.global.exception.DataNotFoundException;
import com.example.sbb.member.domain.Member;
import com.example.sbb.question.domain.Question;
import com.example.sbb.question.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return questionRepository.findAll(pageable);
    }

    public Question getQuestion(Integer id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, Member member) {
        Question question = Question.builder()
                .subject(subject)
                .content(content)
                .createDate(LocalDateTime.now())
                .author(member)
                .build();
        questionRepository.save(question);
    }

}
