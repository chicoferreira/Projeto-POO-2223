package com.marketplace.vintage.questionnaire;

import com.marketplace.vintage.input.InputMapper;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.input.questionnaire.Questionnaire;
import com.marketplace.vintage.input.questionnaire.QuestionnaireAnswers;
import com.marketplace.vintage.input.questionnaire.QuestionnaireBuilder;
import com.marketplace.vintage.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class QuestionnaireTest {

    @Test
    void testConversation() {
        Logger logger = Mockito.mock(Logger.class);
        InputPrompter inputPrompter = Mockito.spy(new InputPrompter());

        Mockito.doReturn("John", "18").when(inputPrompter).getInput();

        Questionnaire questionnaire = QuestionnaireBuilder.newBuilder()
                                                          .withQuestion("name", "What is your name?", InputMapper.STRING)
                                                          .withQuestion("age", "What is your age?", InputMapper.ofIntRange(0, 100))
                                                          .build();

        QuestionnaireAnswers answers = questionnaire.ask(inputPrompter, logger);

        Mockito.verify(logger).print("What is your name? ");
        Mockito.verify(logger).print("What is your age? ");

        assertEquals("John", answers.getAnswer("name", String.class));
        assertEquals(18, answers.getAnswer("age", Integer.class));
    }
}
