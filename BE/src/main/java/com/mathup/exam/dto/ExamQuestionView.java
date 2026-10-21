package com.mathup.exam.dto;

import com.mathup.question.Question;
import com.mathup.question.QuestionType;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * Question shape shown while a student is taking an exam — deliberately omits
 * correctAnswer/hint/explanation so answers aren't leaked before submission.
 */
@Getter
@Builder
public class ExamQuestionView {

    private Long id;
    private String content;
    private QuestionType questionType;
    private List<String> options;

    public static ExamQuestionView from(Question question) {
        return ExamQuestionView.builder()
                .id(question.getId())
                .content(question.getContent())
                .questionType(question.getQuestionType())
                .options(question.getOptions())
                .build();
    }
}
