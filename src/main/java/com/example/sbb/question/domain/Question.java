package com.example.sbb.question.domain;

import com.example.sbb.answer.domain.Answer;
import com.example.sbb.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne
    private Member author;

    private LocalDateTime modifyDate;

    // 상태 변경 메서드
    public void modify(String newSubject, String newContent) {
        // 검증 로직
        if (newSubject == null || newSubject.isEmpty()) {
            throw new IllegalArgumentException("Subject cannot be null or empty");
        }
        if (newContent == null || newContent.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        this.subject = newSubject;
        this.content = newContent;
        this.modifyDate = LocalDateTime.now();
    }

    @ManyToMany
    Set<Member> voter;
}
