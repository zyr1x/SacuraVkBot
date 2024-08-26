package ru.lewis.sacuravkbot.actions;

public abstract class Action {

    private final String actionName;

    public Action(String actionName) {
        this.actionName = actionName;
    }

    public String getActionType() {
        return actionName;
    }
}
