package ru.lewis.sacuravkbot.config;

import com.google.gson.*;
import ru.lewis.sacuravkbot.actions.Action;
import ru.lewis.sacuravkbot.actions.impl.AddTag;
import ru.lewis.sacuravkbot.actions.impl.ChangeKeyBoard;
import ru.lewis.sacuravkbot.actions.impl.MessageAction;
import ru.lewis.sacuravkbot.actions.impl.RemTag;

import java.lang.reflect.Type;

public class ActionDeserializer implements JsonDeserializer<Action> {

    @Override
    public Action deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String actionType = jsonObject.get("actionName").getAsString();  // Используйте actionName для определения типа

        switch (actionType) {

            case "sendMessage": return context.deserialize(json, MessageAction.class);
            case "swapKeyboard": return context.deserialize(json, ChangeKeyBoard.class);
            case "addTag": return context.deserialize(json, AddTag.class);
            case "remTag": return context.deserialize(json, RemTag.class);

        }

        throw new JsonParseException("Иди нахуй чудик, я хуй знает почему не ворк: " + actionType);
    }
}
