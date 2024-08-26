package ru.lewis.sacuravkbot.actions.impl;

import api.longpoll.bots.exceptions.VkApiException;
import api.longpoll.bots.methods.VkBotsMethods;
import api.longpoll.bots.methods.impl.messages.Send;
import ru.lewis.sacuravkbot.actions.Action;

import java.nio.file.Path;
import java.util.List;

public class MessageAction extends Action implements IMessageAction {

    private final String text;

    public MessageAction(String text) {
        super("sendMessage");
        this.text = text;
    }

    @Override
    public void run(VkBotsMethods vk, int peerId, List<Path> files) {
        try {
            Send send = vk.messages.send()
                    .setPeerId(peerId)
                    .setMessage(text);

            for (Path path : files) {
                send.addPhoto(path);
            }

            send.execute();

        } catch (VkApiException e) {
            e.printStackTrace();
        }
    }

    public String getText() {
        return text;
    }
}
