package com.marketplace.vintage.input.questionnaire;

import java.util.HashMap;
import java.util.Map;

public class QuestionnaireAnswers {

    private final Map<String, Object> answers;

    public QuestionnaireAnswers() {
        this.answers = new HashMap<>();
    }

    public void setAnswer(String id, Object answer) {
        this.answers.put(id, answer);
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

    public Map<String, Object> getAnswersMap() {
        return new HashMap<>(this.answers);
    }
}
