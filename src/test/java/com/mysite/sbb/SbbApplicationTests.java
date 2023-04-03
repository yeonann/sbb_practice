package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.question.QuestionService;
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
    @DisplayName("데이터 저장하기")
    void test001() {
        Question q1 = new Question();
        q1.setSubject("sbb가 뭐예요?");
        q1.setContent("sbb에 대해 알고 싶어요.");
        q1.setCreateDate(LocalDateTime.now());
        questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        questionRepository.save(q2);
    }

    @Test
    @DisplayName("데이터 조회하기: findAll")
    void test002() {
        List<Question> all = questionRepository.findAll();
        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("sbb가 뭐예요?", q.getSubject());
    }

    @Test
    @DisplayName("데이터 조회하기: findById")
    void test003() {
        Optional<Question> oq = questionRepository.findById(1);
        if (oq.isPresent()) {
            Question q = oq.get();
            assertEquals("sbb가 뭐예요?", q.getSubject());
        }
    }
    @Test
    @DisplayName("데이터 조회하기: findBySubject")
    void test004() {
        Question q = questionRepository.findBySubject("sbb가 뭐예요?");
        assertEquals(1, q.getId());
    }
    @Test
    @DisplayName("데이터 조회하기: findBySubjectAndContent")
    void test005() {
        Question q = questionRepository.findBySubjectAndContent("sbb가 뭐예요?", "sbb에 대해 알고 싶어요.");
        assertEquals(1,q.getId());
    }
    @Test
    @DisplayName("데이터 조회하기: findBySubjectLike")
    void test006() {
        List<Question> qList = questionRepository.findBySubjectLike("sbb%");
        Question q = qList.get(0);
        assertEquals("sbb가 뭐예요?", q.getSubject());
    }
    @Test
    @DisplayName("데이터 수정하기")
    void test007() {
        Optional<Question> oq = questionRepository.findById(1);
        if (oq.isPresent()) {
            Question q = oq.get();
            q.setSubject("sbb가 무엇인가요?");
            this.questionRepository.save(q);
        }
    }
//    @Test
//    @DisplayName("데이터 삭제하기")
//    void test008() {
//        assertEquals(2, questionRepository.count());
//        Optional<Question> oq = questionRepository.findById(1);
//        assertTrue(oq.isPresent());
//        Question q = oq.get();
//        questionRepository.delete(q);
//        assertEquals(1, questionRepository.count());
//    }
    @Test
    @DisplayName("답변 데이터 생성 후 저장하기")
    void test009() {
        Optional<Question> oq = questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        Answer a = new Answer();
        a.setContent("네, 자동으로 생성됩니다.");
        a.setQuestion(q);
        a.setCreateDate(LocalDateTime.now());
        answerRepository.save(a);
    }
    @Test
    @DisplayName("답변 조회하기")
    void test010() {
        Optional<Answer> oa = answerRepository.findById(1);
        assertTrue(oa.isPresent());
        Answer a = oa.get();
        assertEquals(2, a.getQuestion().getId());
    }
    @Test
    @Transactional
    @DisplayName("답변에 연결된 질문 찾기, 질문에 달린 답변 찾기")
    void test011() {
        Optional<Question> oq = questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        List<Answer> answerList = q.getAnswerList();
        assertEquals(1, answerList.size());
        assertEquals("네, 자동으로 생성됩니다.", answerList.get(0).getContent());
    }
    @Test
    @DisplayName("대량 테스트 데이터 만들기")
    void test012() {
        for (int i =1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다:[%03d}", i);
            String content = "내용 없음";
            questionService.create(subject, content, null);
        }
    }
}