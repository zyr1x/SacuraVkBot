package ru.lewis.sacuravkbot.vk;

import api.longpoll.bots.LongPollBot;
import api.longpoll.bots.exceptions.VkApiException;
import api.longpoll.bots.methods.VkBotsMethods;
import api.longpoll.bots.model.events.messages.MessageEvent;
import api.longpoll.bots.model.events.messages.MessageNew;
import ru.lewis.sacuravkbot.Main;
import ru.lewis.sacuravkbot.actions.Action;
import ru.lewis.sacuravkbot.actions.impl.AddTag;
import ru.lewis.sacuravkbot.actions.impl.ChangeKeyBoard;
import ru.lewis.sacuravkbot.actions.impl.MessageAction;
import ru.lewis.sacuravkbot.actions.impl.RemTag;
import ru.lewis.sacuravkbot.config.keyboard.CFGButton;
import ru.lewis.sacuravkbot.config.Config;
import ru.lewis.sacuravkbot.config.keyboard.KeyBoard;
import ru.lewis.sacuravkbot.config.keyboard.Row;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class VkLongPoll extends LongPollBot {

    @Override
    public void onMessageEvent(MessageEvent event) {
        // https://api.vk.com/method/groups.tagBind?group_id=GROUP_ID&tag_id=TAG_ID&access_token=ACCESS_TOKEN&v=5.131
        if (callBackButton(event)) {
            return;
        }
    }

    @Override
    public void onMessageNew(MessageNew event) {
        this.vk.messages.send();

        try {
            if (event.getMessage().hasText()) {

                if (!event.getMessage().getText().equals("Начать")) {
                    return;
                }

                vk.messages.send()
                        .setPeerId(event.getMessage().getPeerId())
                        .setMessage("Активируем клавиатуру...")
                        .setKeyboard(Utils.getKeyBoard())
                        .execute();

            }
        } catch (VkApiException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getAccessToken() {
        return Config.getInstance().token;
    }

    private boolean callBackButton(MessageEvent event) {

        if (event.getPayload() == null) {
            return false;
        }

        System.out.println(event.getPayload().toString());

        for (KeyBoard keyBoard : Config.getInstance().keyBoards) {

            for (Row row : keyBoard.getRowList()) {

                for (CFGButton cfgButton : row.getCfgButtons()) {


                    if (!event.getPayload().toString().equals("{\"name\":\"" + cfgButton.getButtonName() + "\"}")) {
                        continue;
                    }

                        for (Action action : cfgButton.getActions()) {

                            System.out.println(action.getActionType());

                            switch (action.getActionType()) {
                                case "sendMessage":
                                    ((MessageAction) action).run(vk, event.getPeerId(), cfgButton.getPhotoFilesName().stream()
                                            .map(this::getFilePath)
                                            .collect(Collectors.toList()));
                                    break;

                                case "swapKeyboard":
                                    ((ChangeKeyBoard) action).run(vk, event.getPeerId());
                                    break;

                                case "addTag":
                                    ((AddTag) action).run(event.getUserId());
                                    break;

                                case "remTag":
                                    ((RemTag) action).run(event.getUserId());
                                    break;
                            }
                        }

                         Utils.sendMessageEventAnswer(
                                 event.getEventId(),
                                 event.getUserId(),
                                 event.getPeerId()
                         );

                    return true;
                }
            }

        }

        return false;

    }

    private Path getFilePath(String fileName) {
        try {

            URI jarUri = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI();
            Path jarPath = Paths.get(jarUri).getParent();

            return jarPath.resolve(fileName);

        } catch (URISyntaxException e) {
           e.printStackTrace();
        }
        return null;
    }
}
