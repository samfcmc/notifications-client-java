package org.fenixedu.bennu.notifications.client.json;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.fenixedu.bennu.notifications.client.Notification;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class NotificationJson {

    private static final String DESCRIPTIONS = "descriptions";
    private static final String IMAGE = "image";
    private static final String LINK = "link";
    private static final String TYPE = "type";
    private static final String USERNAMES = "usernames";
    private static final String PAYLOAD = "payload";

    public static JsonObject toJson(Notification notification) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add(USERNAMES, getUsernamesJsonArray(notification.getUsernames()));
        JsonObject payloadJson = new JsonObject();
        payloadJson.addProperty(TYPE, notification.getType());
        payloadJson.addProperty(LINK, notification.getLink());
        payloadJson.addProperty(IMAGE, notification.getImage());
        payloadJson.add(DESCRIPTIONS, getDescriptionsJson(notification.getDescriptions()));
        jsonObject.add(PAYLOAD, payloadJson);
        return jsonObject;
    }

    public static Notification fromJson(JsonObject jsonObject) {
        JsonArray usernames = jsonObject.get(USERNAMES).getAsJsonArray();
        Notification.Builder builder = new Notification.Builder();

        for (JsonElement jsonElement : usernames) {
            String username = jsonElement.getAsString();
            builder.usernames(username);
        }

        JsonObject payloadJson = jsonObject.get(PAYLOAD).getAsJsonObject();
        JsonObject descriptionsJson = payloadJson.get(DESCRIPTIONS).getAsJsonObject();

        for (Entry<String, JsonElement> entry : descriptionsJson.entrySet()) {
            builder.description(entry.getKey(), entry.getValue().getAsString());
        }

        builder.type(payloadJson.get(TYPE).getAsString());
        builder.link(payloadJson.get(LINK).getAsString());
        builder.image(payloadJson.get(IMAGE).getAsString());

        return builder.build();
    }

    private static JsonArray getUsernamesJsonArray(Collection<String> usernames) {
        JsonArray jsonArray = new JsonArray();
        for (String username : usernames) {
            JsonElement usernameElement = new JsonPrimitive(username);
            jsonArray.add(usernameElement);
        }
        return jsonArray;
    }

    private static JsonElement getDescriptionsJson(Map<String, String> descriptions) {
        JsonObject descriptionsJson = new JsonObject();
        for (Entry<String, String> entry : descriptions.entrySet()) {
            descriptionsJson.addProperty(entry.getKey(), entry.getValue());
        }
        return descriptionsJson;
    }

}
