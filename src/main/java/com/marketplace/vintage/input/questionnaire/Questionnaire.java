package com.marketplace.vintage.input.questionnaire;

import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Questionnaire {

    private final InputPrompter inputPrompter;
    private final Logger logger;
    private final List<QuestionnaireQuestion> questions;
    private final Map<String, Object> answers;
    private final Map<String, QuestionnaireQuestion> mappedQuestions;

    public Questionnaire(List<QuestionnaireQuestion> questions, InputPrompter inputPrompter, Logger logger) {
        this.inputPrompter = inputPrompter;
        this.logger = logger;
        this.questions = new LinkedList<>(questions); // ensure order is preserved

        this.answers = new HashMap<>();
        this.mappedQuestions = new HashMap<>();
        for (QuestionnaireQuestion question : questions) {
            this.mappedQuestions.put(question.getId(), question);
        }
    }

    public void askQuestion(String questionId) {
        QuestionnaireQuestion question = this.mappedQuestions.get(questionId);
        if (question == null) {
            throw new IllegalArgumentException("Question with id " + questionId + " does not exist");
        }

        askQuestion(question);
    }

    private void askQuestion(QuestionnaireQuestion question) {
        Object result = inputPrompter.askForInput(this.logger, question.getQuestion(), question.getMapper());
        this.answers.put(question.getId(), result);
    }

    public void ask() {
        for (QuestionnaireQuestion question : this.questions) {
            askQuestion(question.getId());
        }
    }

    public <T> T getAnswer(String name, Class<T> stringClass) {
        Object answer = this.answers.get(name);
        if (answer == null) {
            throw new IllegalArgumentException("Answer with name " + name + " is null or does not exist");
        }

        if (!stringClass.isAssignableFrom(answer.getClass())) {
            throw new IllegalArgumentException("Answer with name " + name + " is not of type " + stringClass.getName());
        }

        return stringClass.cast(answer);
    }
}
