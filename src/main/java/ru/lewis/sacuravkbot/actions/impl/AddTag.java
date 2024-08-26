package ru.lewis.sacuravkbot.actions.impl;

import ru.lewis.sacuravkbot.actions.Action;
import ru.lewis.sacuravkbot.vk.Utils;

public class AddTag extends Action implements IAddTag{

    private final int tagId;

    public AddTag(int tagId) {
        super("addTag");

        this.tagId = tagId;
    }


    @Override
    public void run(int userId) {
        /*
        Utils.tagBind(tagId, userId, "bind");

         */
    }

    public int getTagId() {
        return tagId;
    }
}
