package com.marketplace.vintage.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ViewType {

    USER_LOGIN("User"),
    ADMIN("Admin");

    private static final Map<String, ViewType> VIEW_TYPE_MAP;

    static {
        VIEW_TYPE_MAP = new HashMap<>();
        for (ViewType viewType : ViewType.values()) {
            VIEW_TYPE_MAP.put(viewType.getCommandName(), viewType);
        }
    }

    private final String displayName;

    ViewType(String displayName) {
        this.displayName = displayName;
    }

    public static List<ViewType> getAllViewTypes() {
        return List.of(ViewType.values());
    }

    public static ViewType fromCommandName(String commandName) {
        return VIEW_TYPE_MAP.get(commandName);
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCommandName() {
        return getDisplayName().toLowerCase();
    }
}
