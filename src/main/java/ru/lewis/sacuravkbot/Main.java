package ru.lewis.sacuravkbot;

import api.longpoll.bots.LongPollBot;
import api.longpoll.bots.exceptions.VkApiException;
import ru.lewis.sacuravkbot.config.Config;
import ru.lewis.sacuravkbot.vk.VkLongPoll;

public class Main {

    public static LongPollBot longPollBot;

    public static void main(String[] args) throws VkApiException {

        Config.load("config.json");
        Config.getInstance().toFile("config.json");

        longPollBot = new VkLongPoll();
        longPollBot.startPolling();
    }

}