package com.example.sbb.question.controller;

import com.example.sbb.answer.domain.AnswerForm;
import com.example.sbb.member.domain.Member;
import com.example.sbb.member.service.MemberService;
import com.example.sbb.question.domain.Question;
import com.example.sbb.question.domain.QuestionForm;
import com.example.sbb.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("/question")
@Controller
@RequiredArgsConstructor
public class QuestionController {
    @Autowired
    private final QuestionService questionService;
    private final MemberService memberService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Question> paging = questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Member member = memberService.getMember(principal.getName());
        questionService.create(questionForm.getSubject(), questionForm.getContent(), member);
        return "redirect:/question/list";
    }
}
