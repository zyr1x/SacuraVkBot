package ru.lewis.sacuravkbot.actions.impl;

import ru.lewis.sacuravkbot.actions.Action;
import ru.lewis.sacuravkbot.vk.Utils;

public class RemTag extends Action implements IRemTag {

    private final int tagId;

    public RemTag(int tagId) {
        super("remTag");

        this.tagId = tagId;
    }


    @Override
    public void run(int userId) {
        /*
        Utils.tagBind(tagId, userId, "unbind");

         */
    }

    public int getTagId() {
        return tagId;
    }


}
