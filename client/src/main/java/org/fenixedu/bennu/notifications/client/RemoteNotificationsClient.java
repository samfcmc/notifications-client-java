package org.fenixedu.bennu.notifications.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.fenixedu.bennu.notifications.client.callback.PostNotificationCallback;
import org.fenixedu.bennu.notifications.client.exception.NotificationsClientException;
import org.fenixedu.notifications.client.backend.NotificationsClientBackend;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;

public class RemoteNotificationsClient implements NotificationsClient {

    private static final String DESCRIPTIONS = "descriptions";
    private static final String IMAGE = "image";
    private static final String LINK = "link";
    private static final String TYPE = "type";
    private static final String USERNAMES = "usernames";
    private static final String PAYLOAD = "payload";
    private RemoteClientConfig config;
    private static final String NOTIFICATIONS_ENDPOINT = "/api/notifications";
    private Gson gson;
    private Map<String, String> headers;
    private NotificationsClientBackend backend;

    public RemoteNotificationsClient(RemoteClientConfig config) {
        this.config = config;
        this.gson = new Gson();
        this.headers = new HashMap<>();
        this.headers.put("Content-type", "application/json");
    }

    private JsonArray getUsernamesJsonArray(Collection<String> usernames) {
        JsonArray jsonArray = new JsonArray();
        for (String username : usernames) {
            JsonElement usernameElement = new JsonPrimitive(username);
            jsonArray.add(usernameElement);
        }
        return jsonArray;
    }

    private JsonObject getJsonNotification(Notification notification) {
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

    private JsonElement getDescriptionsJson(Map<String, String> descriptions) {
        JsonObject descriptionsJson = new JsonObject();
        for (Entry<String, String> entry : descriptions.entrySet()) {
            descriptionsJson.addProperty(entry.getKey(), entry.getValue());
        }
        return descriptionsJson;
    }

    private String getEndpointUrl(String endpoint) {
        return this.config.getEndpointUrl(endpoint);
    }

    private void invokePost(String endpoint, String body, Callback<JsonNode> callback) throws NotificationsClientException {
        String url = getEndpointUrl(endpoint);
        Unirest.post(url).headers(headers).body(body).asJsonAsync(callback);
    }

    private void invokePost(String endpoint, JsonElement jsonBody, Callback<JsonNode> callback)
            throws NotificationsClientException {
        invokePost(endpoint, jsonBody.toString(), callback);
    }

    @Override
    public void postNotification(Notification notification) throws NotificationsClientException {
        JsonObject json = getJsonNotification(notification);
        invokePost(NOTIFICATIONS_ENDPOINT, json, new PostNotificationCallback(notification, backend));
    }

}
