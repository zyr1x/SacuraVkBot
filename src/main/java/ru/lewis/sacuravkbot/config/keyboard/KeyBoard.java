package ru.lewis.sacuravkbot.config.keyboard;

import java.util.ArrayList;
import java.util.List;

public class KeyBoard {

    private final String keyboardName;
    private final List<Row> row = new ArrayList<>();

    public KeyBoard(String keyboardName, List<Row> row) {
        this.keyboardName = keyboardName;
        this.row.addAll(row);
    }

    public String getKeyboardName() {
        return keyboardName;
    }

    public List<Row> getRowList() {
        return row;
    }
}
