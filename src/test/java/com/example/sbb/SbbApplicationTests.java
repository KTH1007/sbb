package com.example.sbb;

import com.example.sbb.answer.domain.Answer;
import com.example.sbb.answer.repository.AnswerRepository;
import com.example.sbb.question.domain.Question;
import com.example.sbb.question.repository.QuestionRepository;
import com.example.sbb.question.service.QuestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionService questionService;

    @Test
    @DisplayName("저장 테스트")
    void testJpa() {
        Question q1 = Question.builder()
                .subject("sbb가 무엇인가요?")
                .content("sbb에 대해서 알고 싶습니다.")
                .createDate(LocalDateTime.now())
                .build();
        this.questionRepository.save(q1);

        Question q2 = Question.builder()
                .subject("스프링 부트 모델 질문입니다")
                .content("id는 자동으로 생성 되나요?")
                .createDate(LocalDateTime.now())
                .build();
        this.questionRepository.save(q2);
    }

    @Test
    @DisplayName("질문 데이터 조회")
    void testJpa2() {
        List<Question> all = this.questionRepository.findAll();
        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    @Test
    @DisplayName("Id 값을 이용하여 데이터 조회")
    void testJpa3() {
        Optional<Question> oq = this.questionRepository.findById(1);
        if (oq.isPresent()) {
            Question q = oq.get();
            assertEquals("sbb가 무엇인가요?", q.getSubject());
        }
    }

    @Test
    @DisplayName("제목으로 데이터 조회")
    void testJpa4() {
        Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
        assertEquals(1, q.getId());
    }


    @Test
    @DisplayName("제목 특정 문자열 조회")
    void testJpa5() {
        List<Question> questions = this.questionRepository.findBySubjectLike("sbb%");
        Question q = questions.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    @Test
    @DisplayName("질문 데이터 수정")
    void testJpa6() {
        Optional<Question> oq = this.questionRepository.findById(1);
        assertTrue(oq.isPresent()); // 괄호 안에 값이 true인지 테스트 falst가 뜨면 오류 발생 후 테스트 종료
        Question q = oq.get();
        q.toBuilder()
                .subject("수정된 제목")
                .build();
        this.questionRepository.save(q);
    }

    @Test
    @DisplayName("질문 데이터 삭제")
    void testJpa7() {
        assertEquals(2, this.questionRepository.count());
        Optional<Question> oq = this.questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        this.questionRepository.delete(q);
        assertEquals(1, this.questionRepository.count());
    }

    @Test
    @DisplayName("답변 데이터 저장")
    void testJpa8() {
        Optional<Question> oq = this.questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        Answer a = Answer.builder()
                .content("네 자동으로 생성됩니다.")
                .question(q)
                .createDate(LocalDateTime.now())
                .build();
        this.answerRepository.save(a);
    }

    @Test
    @DisplayName("답변 데이터 조회")
    void testJpa9() {
        Optional<Answer> oa = this.answerRepository.findById(1);
        assertTrue(oa.isPresent());
        Answer a = oa.get();
        assertEquals(2, a.getQuestion().getId());
    }

    @Transactional // 답변 데이터를 가져오는 시점이 달라 오류가 발생하는 것을 막기 위해 사용
    @Test
    @DisplayName("질문 데이터를 통해 답변 데이터 찾기")
    void testJpa10() {
        Optional<Question> oq = this.questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        List<Answer> answerList = q.getAnswerList();

        assertEquals(1, answerList.size());
        assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
    }

    @Test
    void testJpa11() {
        for (int i =1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d]", i);
            String content = "내용 없음 ";
            questionService.create(subject, content);
        }
    }
}
