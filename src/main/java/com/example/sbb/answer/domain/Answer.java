package com.example.sbb.answer.domain;

import com.example.sbb.member.domain.Member;
import com.example.sbb.question.domain.Question;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Question question;

    @ManyToOne
    private Member author;

    private LocalDateTime modifyDate;

    public void modify(String newContent) {
        if (newContent == null || newContent.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }
        this.content = newContent;
        this.modifyDate = LocalDateTime.now();
    }
}
