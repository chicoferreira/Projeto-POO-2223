package com.marketplace.vintage.view.impl;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.commands.item.ItemCommand;
import com.marketplace.vintage.commands.order.OrderCommand;
import com.marketplace.vintage.commands.shoppingcart.ShoppingCartCommand;
import com.marketplace.vintage.commands.stats.StatsCommand;
import com.marketplace.vintage.commands.time.CurrentTimeCommand;
import com.marketplace.vintage.commands.user.UserCommandUserView;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.logging.PrefixLogger;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.view.BaseView;
import org.jetbrains.annotations.NotNull;

public class UserView extends BaseView {

    private final User user;

    public UserView(Logger logger,
                    InputPrompter inputPrompter,
                    Vintage vintage,
                    User user) {
        super(PrefixLogger.of(user.getName(), logger), inputPrompter, "user view");
        this.user = user;

        this.getCommandManager().registerCommand(new UserCommandUserView(this));
        this.getCommandManager().registerCommand(new ItemCommand(this, vintage));
        this.getCommandManager().registerCommand(new OrderCommand(this, vintage));
        this.getCommandManager().registerCommand(new ShoppingCartCommand(this, vintage));
        this.getCommandManager().registerCommand(new CurrentTimeCommand(vintage, "time"));
        this.getCommandManager().registerCommand(new StatsCommand(vintage));
    }

    @Override
    public void run() {
        getLogger().info("Logged in as " + user.getName() + " (" + user.getEmail() + ")");

        super.run();
    }

    @NotNull
    public User getCurrentLoggedInUser() {
        if (this.user == null) {
            throw new IllegalStateException("User not logged in");
        }
        return this.user;
    }
}
