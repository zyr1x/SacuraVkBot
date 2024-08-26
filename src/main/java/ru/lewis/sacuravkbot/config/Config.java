package ru.lewis.sacuravkbot.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.lewis.sacuravkbot.actions.Action;
import ru.lewis.sacuravkbot.actions.impl.AddTag;
import ru.lewis.sacuravkbot.actions.impl.ChangeKeyBoard;
import ru.lewis.sacuravkbot.actions.impl.MessageAction;
import ru.lewis.sacuravkbot.actions.impl.RemTag;
import ru.lewis.sacuravkbot.config.keyboard.CFGButton;
import ru.lewis.sacuravkbot.config.keyboard.KeyBoard;
import ru.lewis.sacuravkbot.config.keyboard.Row;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {

    public String token;
    public String groupId;
    public List<Integer> adminsId;
    public List<KeyBoard> keyBoards;

    public Config() {

        this.keyBoards = new ArrayList<>();
        this.adminsId = new ArrayList<>();

        /**
         * KEYBOARD # 1
         */

        Row row = new Row();
        Row row2 = new Row();

        // button 1

        CFGButton cfgButton1 = new CFGButton(
                "\uD83C\uDF40 Sacura Free v1.0",
                "POSITIVE",
                Arrays.asList(new MessageAction("Пока-что фри версия еще не вышла, но вы продолжайте ждать.")));

        cfgButton1.getPhotoFilesName().add("screenshot1.png");
        cfgButton1.getPhotoFilesName().add("screenshot2.png");

        // button 2

        CFGButton cfgButton2 = new CFGButton(
                "\uD83C\uDFAE Sacura Premium v1.0",
                "NEGATIVE",
                Arrays.asList(new MessageAction("Воу-воу, купить нашу платную версию пока что невозможно, так как, она еще не релизнулась ;D")));

        // button 3

        CFGButton cfgButton3 = new CFGButton(
                "Техническая Поддержка",
                "PRIMARY",
                Arrays.asList(new ChangeKeyBoard("cancel", "Вы успешно обратились в тп, ожидайте, пожалуйста, ответа администратора."),
                        new AddTag(1406)
                ));

        row.getCfgButtons().add(cfgButton1);
        row.getCfgButtons().add(cfgButton2);
        row2.getCfgButtons().add(cfgButton3);

        KeyBoard keyBoard = new KeyBoard("main", List.of(row, row2));

        /**
         * KEYBOARD # 2
         */

        Row row3 = new Row();

        CFGButton cfgButton4 = new CFGButton(
                "Отменить запрос в ТП",
                "SECONDARY",
                Arrays.asList(new ChangeKeyBoard("main", "Вы успешно отменили свой запрос в тп. Удачного дня!"),
                        new RemTag(1406)));

        row3.getCfgButtons().add(cfgButton4);

        KeyBoard keyBoard2 = new KeyBoard("cancel", List.of(row3));

        /**
         * STOP MACHINE
         */

        this.keyBoards.add(keyBoard);
        this.keyBoards.add(keyBoard2);

        //this.TOKEN = "<enter token>";
        this.token = "vk1.a.hI43AWY20LqwCWpziFAVKHj4vLltQmh4zapwRHiFPjCqPxcOubnTRw3ee4mtiKmYa0lAvovO17tOzXk9wQ0YZRnO009KfUdSDPS12042Wn36CDB57Vi9y7THoDGkgRyaii37f0BI1DjDuBZZdDkw_aOLpQ5m4_5vopF3Z1UYX6LcYyE1GcNQF_0gwAI0yuWzX9UKYykCxaTFh4Kokk_vYA";
        this.groupId = "226674711";
        this.adminsId.add(861628021);
    }

    private static Config instance;

    public static Config getInstance() {
        if (instance == null) {
            instance = fromDefaults();
        }
        return instance;
    }

    public static void load(File file) {
        instance = fromFile(file);

        if (instance == null) {
            instance = fromDefaults();
        }
    }

    public static void load(String file) {
        load(new File(file));
    }

    private static Config fromDefaults() {
        Config config = new Config();
        return config;
    }

    public void toFile(String file) {
        toFile(new File(file));
    }

    public void toFile(File file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonConfig = gson.toJson(this);
        FileWriter writer;
        try {
            writer = new FileWriter(file);
            writer.write(jsonConfig);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Config fromFile(File configFile) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Action.class, new ActionDeserializer())
                    .setPrettyPrinting()
                    .create();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
            return gson.fromJson(reader, Config.class);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
