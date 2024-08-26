package ru.lewis.sacuravkbot.actions.impl;

import api.longpoll.bots.exceptions.VkApiException;
import api.longpoll.bots.methods.VkBotsMethods;
import ru.lewis.sacuravkbot.actions.Action;
import ru.lewis.sacuravkbot.vk.Utils;

public class ChangeKeyBoard extends Action implements IChangeKeyBoard {

    private final String keyBoardName;
    private final String text;

    public ChangeKeyBoard(String keyBoardName, String text) {
        super("swapKeyboard");

        this.keyBoardName = keyBoardName;
        this.text = text;
    }

    @Override
    public void run(VkBotsMethods vk, int peerId) {
        try {

            System.out.println("ору");

            vk.messages.send()
                    .setPeerId(peerId)
                    .setMessage(text)
                    .setKeyboard(Utils.getKeyBoard(keyBoardName))
                    .execute();

            System.out.println("success");

        } catch (VkApiException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public String getKeyBoardName() {
        return keyBoardName;
    }
}
