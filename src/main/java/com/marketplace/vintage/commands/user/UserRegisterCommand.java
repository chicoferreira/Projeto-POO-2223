package com.marketplace.vintage.commands.user;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputMapper;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.input.questionnaire.Questionnaire;
import com.marketplace.vintage.input.questionnaire.QuestionnaireAnswers;
import com.marketplace.vintage.input.questionnaire.QuestionnaireBuilder;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.User;

public class UserRegisterCommand extends BaseCommand {

    private final Vintage vintage;
    private final Questionnaire createUserQuestionnaire;

    public UserRegisterCommand(Vintage vintage) {
        super("register", "user register <email>", 1, "Register a new user");
        this.vintage = vintage;

        this.createUserQuestionnaire = QuestionnaireBuilder.newBuilder()
                .withQuestion("username", "Enter your username:", username -> {
                    vintage.validateUsername(username);
                    return username;
                })
                .withQuestion("name", "Enter your name:", InputMapper.STRING)
                .withQuestion("address", "Enter your address:", InputMapper.STRING)
                .withQuestion("taxNumber", "Enter your tax number:", InputMapper.STRING)
                .build();
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        String email = args[0];

        User user = askUserInfoAndRegister(email, inputPrompter, logger);
        logger.info("Registered " + user.getName() + " (" + user.getEmail() + ").");
    }

    public User askUserInfoAndRegister(String email, InputPrompter inputPrompter, Logger logger) {
        QuestionnaireAnswers answers = createUserQuestionnaire.ask(inputPrompter, logger);

        String username = answers.getAnswer("username", String.class);
        String name = answers.getAnswer("name", String.class);
        String address = answers.getAnswer("address", String.class);
        String taxNumber = answers.getAnswer("taxNumber", String.class);

        return vintage.createUser(username, email, name, address, taxNumber);
    }

}
