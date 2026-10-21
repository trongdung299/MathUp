package com.mathup.submission;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mathup.question.Question;
import com.mathup.question.QuestionType;
import org.junit.jupiter.api.Test;

class AnswerGraderTest {

    private static Question question(QuestionType type, String correctAnswer) {
        return Question.builder().questionType(type).correctAnswer(correctAnswer).build();
    }

    @Test
    void multipleChoice_matchesIgnoringCaseAndSpaces() {
        Question q = question(QuestionType.MULTIPLE_CHOICE, "B");
        assertEquals(1.0, AnswerGrader.grade(q, " b "));
        assertEquals(0.0, AnswerGrader.grade(q, "C"));
        assertEquals(0.0, AnswerGrader.grade(q, null));
    }

    @Test
    void trueFalse_partialCreditByNumberOfCorrectStatements() {
        Question q = question(QuestionType.TRUE_FALSE, "ĐSĐS");
        assertEquals(1.0, AnswerGrader.grade(q, "ĐSĐS"));
        assertEquals(0.5, AnswerGrader.grade(q, "ĐSĐĐ"));   // 3 ý đúng
        assertEquals(0.25, AnswerGrader.grade(q, "ĐSSĐ"));  // 2 ý đúng
        assertEquals(0.1, AnswerGrader.grade(q, "ĐĐSĐ"));   // 1 ý đúng
        assertEquals(0.0, AnswerGrader.grade(q, "SĐSĐ"));   // 0 ý đúng
    }

    @Test
    void trueFalse_acceptsSeparatorsAndAlternativeLetters() {
        Question q = question(QuestionType.TRUE_FALSE, "Đ,S,Đ,S");
        assertEquals(1.0, AnswerGrader.grade(q, "t f t f"));
        assertEquals(1.0, AnswerGrader.grade(q, "d-s-d-s"));
    }

    @Test
    void trueFalse_wrongLengthOrNullGetsNothing() {
        Question q = question(QuestionType.TRUE_FALSE, "ĐSĐS");
        assertEquals(0.0, AnswerGrader.grade(q, "ĐS"));
        assertEquals(0.0, AnswerGrader.grade(q, null));
    }

    @Test
    void trueFalse_legacySingleAnswerStillExactMatch() {
        Question q = question(QuestionType.TRUE_FALSE, "Đúng");
        assertEquals(1.0, AnswerGrader.grade(q, "đúng"));
        assertEquals(0.0, AnswerGrader.grade(q, "Sai"));
    }

    @Test
    void shortAnswer_comparesNumerically() {
        Question q = question(QuestionType.SHORT_ANSWER, "2.5");
        assertEquals(1.0, AnswerGrader.grade(q, "2,5"));
        assertEquals(1.0, AnswerGrader.grade(q, "2.50"));
        assertEquals(0.0, AnswerGrader.grade(q, "2.6"));
    }

    @Test
    void shortAnswer_nonNumericFallsBackToTextMatch() {
        Question q = question(QuestionType.SHORT_ANSWER, "pi/2");
        assertEquals(1.0, AnswerGrader.grade(q, " PI/2 "));
        assertEquals(0.0, AnswerGrader.grade(q, "pi/3"));
    }
}
