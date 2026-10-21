package com.mathup.submission;

import com.mathup.question.Question;
import java.math.BigDecimal;

/** Chấm 1 câu, trả về tỉ lệ điểm đạt được trong khoảng 0..1. */
final class AnswerGrader {

    // Thang điểm Part II đề THPTQG theo số ý đúng (0..4): 0 / 0,1 / 0,25 / 0,5 / 1
    private static final double[] TRUE_FALSE_SCALE = {0, 0.1, 0.25, 0.5, 1};
    private static final int TRUE_FALSE_STATEMENTS = 4;

    private AnswerGrader() {
    }

    static double grade(Question question, String submitted) {
        if (submitted == null) {
            return 0;
        }
        String correct = question.getCorrectAnswer();
        return switch (question.getQuestionType()) {
            case TRUE_FALSE -> gradeTrueFalse(correct, submitted);
            case SHORT_ANSWER -> gradeShortAnswer(correct, submitted);
            default -> textEquals(correct, submitted) ? 1 : 0;
        };
    }

    private static double gradeTrueFalse(String correct, String submitted) {
        String expected = normalizeStatements(correct);
        // Câu cũ chỉ có 1 đáp án duy nhất (vd. "Đúng"/"Sai") → so khớp nguyên văn
        if (expected.length() != TRUE_FALSE_STATEMENTS) {
            return textEquals(correct, submitted) ? 1 : 0;
        }
        String actual = normalizeStatements(submitted);
        if (actual.length() != TRUE_FALSE_STATEMENTS) {
            return 0;
        }
        int matched = 0;
        for (int i = 0; i < TRUE_FALSE_STATEMENTS; i++) {
            if (expected.charAt(i) == actual.charAt(i)) {
                matched++;
            }
        }
        return TRUE_FALSE_SCALE[matched];
    }

    /** "Đ,S,Đ,S" / "dsds" / "T F T F" → "TFTF" (bỏ ký tự phân cách, Đ/D/T = đúng, S/F = sai). */
    private static String normalizeStatements(String value) {
        StringBuilder sb = new StringBuilder();
        for (char c : value.toUpperCase().toCharArray()) {
            switch (c) {
                case 'Đ', 'D', 'T' -> sb.append('T');
                case 'S', 'F' -> sb.append('F');
                default -> {
                    if (Character.isLetterOrDigit(c)) {
                        sb.append(c);
                    }
                }
            }
        }
        return sb.toString();
    }

    private static double gradeShortAnswer(String correct, String submitted) {
        try {
            return toNumber(correct).compareTo(toNumber(submitted)) == 0 ? 1 : 0;
        } catch (NumberFormatException e) {
            return textEquals(correct, submitted) ? 1 : 0;
        }
    }

    private static BigDecimal toNumber(String value) {
        return new BigDecimal(value.trim().replace(',', '.'));
    }

    private static boolean textEquals(String correct, String submitted) {
        return submitted.trim().equalsIgnoreCase(correct.trim());
    }
}
