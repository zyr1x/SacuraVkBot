package ru.lewis.sacuravkbot.actions.impl;

import api.longpoll.bots.methods.VkBotsMethods;

public interface IChangeKeyBoard {

    public void run(VkBotsMethods vk, int peerId);

}
