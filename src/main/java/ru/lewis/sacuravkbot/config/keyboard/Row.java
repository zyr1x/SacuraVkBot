package ru.lewis.sacuravkbot.config.keyboard;

import java.util.ArrayList;
import java.util.List;

public class Row {

    private final List<CFGButton> cfgButtons;

    public Row() {
        this.cfgButtons = new ArrayList<>();
    }

    public List<CFGButton> getCfgButtons() {
        return cfgButtons;
    }
}
