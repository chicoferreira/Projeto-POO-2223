package com.marketplace.vintage.commands.item;

import com.marketplace.vintage.VintageController;
import com.marketplace.vintage.carrier.ParcelCarrier;
import com.marketplace.vintage.carrier.ParcelCarrierManager;
import com.marketplace.vintage.command.BaseCommand;
import com.marketplace.vintage.input.InputMapper;
import com.marketplace.vintage.input.questionnaire.QuestionnaireBuilder;
import com.marketplace.vintage.item.Item;
import com.marketplace.vintage.item.ItemProperty;
import com.marketplace.vintage.item.ItemType;
import com.marketplace.vintage.item.impl.TshirtItem;
import com.marketplace.vintage.logging.Logger;
import com.marketplace.vintage.utils.StringUtils;
import com.marketplace.vintage.view.impl.UserView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ItemCreateCommand extends BaseCommand {

    private final UserView userView;
    private final ParcelCarrierManager parcelCarrierManager;
    private final VintageController vintageController;

    public ItemCreateCommand(UserView userView, ParcelCarrierManager parcelCarrierManager, VintageController vintageController) {
        super("create", "item create", 0, "Create a new item");
        this.userView = userView;
        this.parcelCarrierManager = parcelCarrierManager;
        this.vintageController = vintageController;
    }

    private static final List<String> ITEM_TYPES_DISPLAY_NAMES = Arrays.stream(ItemType.values()).map(ItemType::getDisplayName).collect(Collectors.toList());

    // These methods are here because they are only related to this command and would mess up the ItemProperty enum a lot if used there
    private String getQuestion(ItemProperty itemProperty) {
        return switch (itemProperty) {
            case ITEM_CONDITION -> "Insert the item condition (new, used):";
            case DESCRIPTION -> "Insert the item description:";
            case BRAND -> "Insert the item brand:";
            case BASE_PRICE -> "Insert the item base price:";
            case PARCEL_CARRIER_UUID -> "Insert the item parcel carrier to use:";
            case DIMENSION_AREA -> "Insert the bag dimension area:";
            case MATERIAL -> "Insert the item material:";
            case COLLECTION_YEAR -> "Insert the item collection year:";
            case APPRECIATION_RATE_OVER_YEARS -> "Insert the item appreciation rate over years in percentage (0-100):";
            case SAPATILHA_SIZE -> "Insert the sapatilha size (5-50):";
            case HAS_LACES -> "Insert if the sapatilha has laces (y/n):";
            case COLOR -> "Insert the item color:";
            case TSHIRT_SIZE -> "Insert the tshirt size (small, medium, large, extra large):";
            case TSHIRT_PATTERN -> "Insert the tshirt pattern (plain, stripes, palm trees):";
        };
    }

    private Function<String, ?> getMapper(ItemProperty itemProperty, Logger logger) {
        return switch (itemProperty) {
            case ITEM_CONDITION -> InputMapper.ofItemCondition(getInputPrompter(), logger);
            case DESCRIPTION, BRAND, MATERIAL, COLOR -> InputMapper.STRING;
            case BASE_PRICE -> InputMapper.BIG_DECIMAL;
            case PARCEL_CARRIER_UUID -> InputMapper.ofParcelCarrier(parcelCarrierManager).andThen(ParcelCarrier::getId);
            case DIMENSION_AREA -> InputMapper.ofIntRange(1, 100);
            case COLLECTION_YEAR -> InputMapper.ofIntRange(1000, 3000);
            case APPRECIATION_RATE_OVER_YEARS -> InputMapper.ofIntRange(0, 100);
            case SAPATILHA_SIZE -> InputMapper.ofIntRange(5, 50);
            case HAS_LACES -> InputMapper.BOOLEAN;
            case TSHIRT_SIZE -> InputMapper.ofEnumValues(TshirtItem.TshirtItemSize.class);
            case TSHIRT_PATTERN -> InputMapper.ofEnumValues(TshirtItem.TshirtItemPattern.class);
        };
    }

    @Override
    protected void executeSafely(Logger logger, String[] args) {
        if (parcelCarrierManager.getAll().isEmpty()) {
            logger.warn("There are no parcel carriers available. Please contact the administrator.");
            return;
        }

        logger.info("Choose the type of item you want to create:");
        logger.info(StringUtils.joinQuoted(ITEM_TYPES_DISPLAY_NAMES, ", "));

        ItemType itemType = getInputPrompter().askForInput(logger, "Insert the item type:", ItemType::fromDisplayName);

        QuestionnaireBuilder questionnaireBuilder = QuestionnaireBuilder.newBuilder();

        Set<ItemProperty> itemProperties = itemType.getRequiredProperties();

        for (ItemProperty itemProperty : itemProperties) {
            questionnaireBuilder.withQuestion(itemProperty.name(), getQuestion(itemProperty), getMapper(itemProperty, logger));
        }

        Map<String, Object> answersMap = questionnaireBuilder.build().ask(getInputPrompter(), logger).getAnswersMap();

        Map<ItemProperty, Object> itemPropertiesMap = new HashMap<>();
        for (var entry : answersMap.entrySet()) {
            itemPropertiesMap.put(ItemProperty.valueOf(entry.getKey()), entry.getValue());
        }

        Item item = vintageController.registerNewItem(userView.getCurrentLoggedInUser(), itemType, itemPropertiesMap);

        logger.info("Registered item " + itemType.getDisplayName() + " (" + item.getAlphanumericId() + ") successfully.");
    }
}
