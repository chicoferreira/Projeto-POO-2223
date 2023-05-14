package com.marketplace.vintage.commands.stats;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.utils.StringUtils;
import com.marketplace.vintage.utils.VintageDate;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;

public class TopListStatsCommand extends BaseStatsCommand {

    public TopListStatsCommand(Vintage vintage) {
        super(vintage, "toplist", "stats toplist (limit) (from) (to)", 0, "Shows the top list of sellers and buyers in a given date range or all time");
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        int limit = 5;
        if (args.length > 0) {
            try {
                limit = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                logger.warn("Couldn't parse list limit from '" + args[0] + "'.");
                return;
            }
        }

        if (limit < 1) {
            logger.warn("List limit must be greater than 0.");
            return;
        }

        Predicate<VintageDate> datePredicate = getDatePredicate(args, 1);

        List<User> topBuyers = getVintage().getTopBuyers(limit, datePredicate);
        List<User> topSellers = getVintage().getTopSellers(limit, datePredicate);

        if (topBuyers.isEmpty()) {
            logger.warn("No buyers found in the specified date range.");
        } else {
            logger.info("Top " + limit + " buyers in specified date range:");
            int i = 1;
            for (User buyer : topBuyers) {
                BigDecimal spent = getVintage().getMoneySpentByUserInDatePredicate(buyer, datePredicate);
                logger.info(" #" + i + " " + buyer.getName() + " - " + StringUtils.formatCurrency(spent));
                i++;
            }
        }

        logger.info();

        if (topSellers.isEmpty()) {
            logger.warn("No sellers found in the specified date range.");
        } else {
            logger.info("Top " + limit + " sellers in specified date range:");
            int i = 1;
            for (User seller : topSellers) {
                BigDecimal salesMoney = getVintage().getMoneyFromUserSalesByDatePredicate(seller, datePredicate);
                logger.info(" #" + i + " " + seller.getName() + " - " + StringUtils.formatCurrency(salesMoney));
                i++;
            }
        }


    }
}
