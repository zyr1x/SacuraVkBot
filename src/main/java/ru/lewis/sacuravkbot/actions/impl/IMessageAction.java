package ru.lewis.sacuravkbot.actions.impl;

import api.longpoll.bots.methods.VkBotsMethods;

import java.nio.file.Path;
import java.util.List;

public interface IMessageAction {

    public void run(VkBotsMethods vk, int peerId, List<Path> files);
}
