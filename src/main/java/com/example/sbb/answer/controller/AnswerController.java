package com.example.sbb.answer.controller;

import com.example.sbb.answer.domain.AnswerForm;
import com.example.sbb.answer.service.AnswerService;
import com.example.sbb.member.domain.Member;
import com.example.sbb.member.service.MemberService;
import com.example.sbb.question.domain.Question;
import com.example.sbb.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@RequestMapping("/answer")
@Controller
@RequiredArgsConstructor // 자동으로 생성자 주입
public class AnswerController {

    @Autowired
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
        Question question = questionService.getQuestion(id);
        Member member = memberService.getMember(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        answerService.create(question, answerForm.getContent(), member);
        return String.format("redirect:/question/detail/%s", id);
    }
}
