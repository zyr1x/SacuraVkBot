package ru.lewis.sacuravkbot.config.keyboard;

import ru.lewis.sacuravkbot.actions.Action;

import java.util.ArrayList;
import java.util.List;

public class CFGButton {

    private final String color;
    private final String buttonName;
    private final List<Action> actions = new ArrayList<>();
    private final List<String> photoFilesName;

    public CFGButton(String buttonName, String color, List<Action> actions) {
        this.color = color;
        this.buttonName = buttonName;
        this.actions.addAll(actions);
        this.photoFilesName = new ArrayList<>();
    }

    public String getButtonName() {
        return buttonName;
    }

    public String getColor() {
        return color;
    }

    public List<Action> getActions() {
        return actions;
    }

    public List<String> getPhotoFilesName() {
        return photoFilesName;
    }
}
