package com.marketplace.vintage.commands.stats;

import com.marketplace.vintage.Vintage;
import com.marketplace.vintage.input.InputPrompter;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.user.User;
import com.marketplace.vintage.utils.StringUtils;
import com.marketplace.vintage.utils.VintageDate;

import java.math.BigDecimal;
import java.util.function.Predicate;

public class TopSellerStatsCommand extends BaseStatsCommand {

    public TopSellerStatsCommand(Vintage vintage) {
        super(vintage, "topseller", "stats topseller (from) (to)", 0, "Shows the best seller between two dates or all time");
    }

    @Override
    protected void executeSafely(Logger logger, InputPrompter inputPrompter, String[] args) {
        Predicate<VintageDate> datePredicate = getDatePredicate(args);
        User bestSeller = getVintage().getBestSeller(datePredicate);

        if (bestSeller == null) {
            logger.warn("No best seller found in the specified date range.");
            return;
        }

        BigDecimal total = getVintage().getMoneyFromUserSalesByDatePredicate(bestSeller, datePredicate);
        logger.info("Top user seller in specified date range is " + bestSeller.getName() + " with " + StringUtils.formatCurrency(total) + " made in sales.");
    }
}
