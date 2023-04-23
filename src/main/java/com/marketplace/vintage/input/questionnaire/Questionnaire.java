package com.marketplace.vintage.input.questionnaire;

import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;

import java.util.LinkedList;
import java.util.List;

public class Questionnaire {

    private final List<QuestionnaireQuestion> questions;

    public Questionnaire(List<QuestionnaireQuestion> questions) {
        this.questions = new LinkedList<>(questions); // ensure order is preserved
    }

    private Object askQuestion(QuestionnaireQuestion question, InputPrompter inputPrompter, Logger logger) {
        return inputPrompter.askForInput(logger, question.getQuestion(), question.getMapper());
    }

    public QuestionnaireAnswers ask(InputPrompter inputPrompter, Logger logger) {
        QuestionnaireAnswers answers = new QuestionnaireAnswers();
        for (QuestionnaireQuestion question : this.questions) {
            answers.setAnswer(question.getId(), askQuestion(question, inputPrompter, logger));
        }
        return answers;
    }
}
