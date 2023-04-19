package com.marketplace.vintage.input.questionnaire;

import java.util.function.Function;

public class QuestionnaireQuestion {

    private final String id;
    private final String question;
    private final Function<String, ?> mapper;

    public QuestionnaireQuestion(String id, String question, Function<String, ?> mapper) {
        this.id = id;
        this.question = question;
        this.mapper = mapper;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public Function<String, ?> getMapper() {
        return mapper;
    }
}
