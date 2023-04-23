package com.marketplace.vintage.input.questionnaire;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class QuestionnaireBuilder {

    private final List<QuestionnaireQuestion> questions;

    private QuestionnaireBuilder() {
        this.questions = new LinkedList<>(); // ensure order of questions is preserved
    }

    public static QuestionnaireBuilder newBuilder() {
        return new QuestionnaireBuilder();
    }

    private boolean questionExists(String id) {
        return this.questions.stream().anyMatch(question -> question.getId().equals(id));
    }

    public QuestionnaireBuilder withQuestion(String id, String question, Function<String, ?> conversationParameter) {
        if (questionExists(id)) {
            throw new IllegalArgumentException("Question with id " + id + " already exists");
        }

        this.questions.add(new QuestionnaireQuestion(id, question, conversationParameter));
        return this;
    }

    public Questionnaire build() {
        return new Questionnaire(this.questions);
    }
}
