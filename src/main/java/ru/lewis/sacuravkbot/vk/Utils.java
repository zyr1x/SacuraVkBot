package ru.lewis.sacuravkbot.vk;

import api.longpoll.bots.methods.impl.VkMethod;
import api.longpoll.bots.model.objects.additional.Keyboard;
import api.longpoll.bots.model.objects.additional.buttons.Button;
import api.longpoll.bots.model.objects.additional.buttons.CallbackButton;
import com.google.gson.JsonObject;
import ru.lewis.sacuravkbot.Main;
import ru.lewis.sacuravkbot.config.Config;
import ru.lewis.sacuravkbot.config.keyboard.CFGButton;
import ru.lewis.sacuravkbot.config.keyboard.Row;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Utils {

    private static String buildQueryString(Map<String, String> params) throws Exception {
        StringBuilder queryString = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (queryString.length() > 0) {
                queryString.append("&");
            }
            queryString.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.name()));
            queryString.append("=");
            queryString.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name()));
        }
        return queryString.toString();
    }

    public static void sendMessageEventAnswer(String eventId, int userId, int peerId) {
        String urlString = "https://api.vk.com/method/messages.sendMessageEventAnswer";
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);

            Map<String, String> params = new HashMap<>();
            params.put("user_id", String.valueOf(userId));
            params.put("event_id", eventId);
            params.put("access_token", Main.longPollBot.getAccessToken());
            params.put("peer_id", String.valueOf(peerId));
            params.put("v", "5.199");

            String queryString = buildQueryString(params);
            try (OutputStream os = connection.getOutputStream()) {
                os.write(queryString.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            try (Scanner scanner = new Scanner(connection.getInputStream())) {
                String response = scanner.useDelimiter("\\A").next();
                System.out.println("Response: " + response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void tagBind(int tagId, int userId, String act) {
        String urlString = "https://api.vk.com/method/groups.tagBind";
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);

            Map<String, String> params = new HashMap<>();
            params.put("group_id", Config.getInstance().groupId);
            params.put("tag_id", String.valueOf(tagId));
            params.put("access_token", Main.longPollBot.getAccessToken());
            params.put("user_id", String.valueOf(userId));
            params.put("act", act);
            params.put("v", "5.199");

            String queryString = buildQueryString(params);
            try (OutputStream os = connection.getOutputStream()) {
                os.write(queryString.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            try (Scanner scanner = new Scanner(connection.getInputStream())) {
                String response = scanner.useDelimiter("\\A").next();
                System.out.println("Response: " + response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Keyboard getKeyBoard() throws NoSuchFieldException {

        List<List<Button>> rows = new ArrayList<>();

        for (Row row : Config.getInstance().keyBoards.stream().filter(k -> k.getKeyboardName().equals("main"))
                .findFirst()
                .orElseThrow(NoSuchFieldException::new).getRowList()) {


            List<Button> buttons = new ArrayList<>();

            for (CFGButton cfgButton : row.getCfgButtons()) {

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("name", cfgButton.getButtonName());

                Button button = new CallbackButton(Button.Color.valueOf(cfgButton.getColor()), new CallbackButton.Action(cfgButton.getButtonName(), jsonObject));
                buttons.add(button);
            }

            rows.add(buttons);
        }

        return new Keyboard(rows);
    }

    public static Keyboard getKeyBoard(String keyBoardName) throws NoSuchFieldException {

        List<List<Button>> rows = new ArrayList<>();

        for (Row row : Config.getInstance().keyBoards.stream().filter(k -> k.getKeyboardName().equals(keyBoardName))
                .findFirst()
                .orElseThrow(NoSuchFieldException::new).getRowList()) {


            List<Button> buttons = new ArrayList<>();

            for (CFGButton cfgButton : row.getCfgButtons()) {

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("name", cfgButton.getButtonName());

                Button button = new CallbackButton(Button.Color.valueOf(cfgButton.getColor()), new CallbackButton.Action(cfgButton.getButtonName(), jsonObject));
                buttons.add(button);
            }

            rows.add(buttons);
        }

        return new Keyboard(rows);
    }

}
