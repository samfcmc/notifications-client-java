package org.fenixedu.notifications.client.ff.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.fenixedu.bennu.notifications.client.Notification;
import org.fenixedu.bennu.notifications.client.json.NotificationJson;
import org.fenixedu.notifications.client.backend.NotificationsClientBackend;
import org.fenixedu.notifications.client.ff.domain.NotificationsSystem;
import org.fenixedu.notifications.client.ff.domain.PendingNotification;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class NotificationsClientFFBackend implements NotificationsClientBackend {

    @Override
    public void storePendingNotification(Notification notification) {
        JsonArray usernames = getUsernamesJsonArray(notification.getUsernames());
        JsonObject payload = getPayloadJsonObject(notification);

        new PendingNotification(usernames, payload);
    }

    private JsonObject getPayloadJsonObject(Notification notification) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("link", notification.getLink());
        jsonObject.addProperty("image", notification.getImage());
        jsonObject.addProperty("type", notification.getType());
        jsonObject.add("descriptions", getDescriptionsJson(notification));

        return jsonObject;
    }

    private JsonElement getDescriptionsJson(Notification notification) {
        JsonObject jsonObject = new JsonObject();
        for (Entry<String, String> entry : notification.getDescriptions().entrySet()) {
            jsonObject.addProperty(entry.getKey(), entry.getValue());
        }
        return jsonObject;
    }

    private JsonArray getUsernamesJsonArray(Collection<String> usernames) {
        JsonArray jsonArray = new JsonArray();
        for (String username : usernames) {
            jsonArray.add(new JsonPrimitive(username));
        }
        return jsonArray;
    }

    @Override
    public Collection<Notification> getPendingNotifications() {
        List<Notification> list = new ArrayList<>();

        for (PendingNotification pendingNotification : NotificationsSystem.getInstance().getPendingNotificationSet()) {
            Notification notification = getNotification(pendingNotification);
            list.add(notification);
        }

        return list;
    }

    private Notification getNotification(PendingNotification pendingNotification) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("usernames", pendingNotification.getUsernames());
        jsonObject.add("payload", pendingNotification.getPayload());

        return NotificationJson.fromJson(jsonObject);
    }

}
