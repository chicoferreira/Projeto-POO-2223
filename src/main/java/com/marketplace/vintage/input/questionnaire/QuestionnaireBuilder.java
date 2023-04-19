package com.marketplace.vintage.input.questionnaire;

import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class QuestionnaireBuilder {

    private final List<QuestionnaireQuestion> questions;
    private InputPrompter inputPrompter;
    private Logger logger;

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


    public QuestionnaireBuilder withInputPrompter(InputPrompter inputPrompter) {
        this.inputPrompter = inputPrompter;
        return this;
    }

    public QuestionnaireBuilder withLogger(Logger logger) {
        this.logger = logger;
        return this;
    }

    public Questionnaire build() {
        if (this.inputPrompter == null) {
            throw new IllegalArgumentException("InputPrompter must be set");
        }

        if (this.logger == null) {
            throw new IllegalArgumentException("Logger must be set");
        }

        return new Questionnaire(this.questions, this.inputPrompter, this.logger);
    }
}
