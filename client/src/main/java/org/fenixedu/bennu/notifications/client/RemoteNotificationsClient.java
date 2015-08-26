package org.fenixedu.bennu.notifications.client;

import java.util.HashMap;
import java.util.Map;

import org.fenixedu.bennu.notifications.client.callback.PostNotificationCallback;
import org.fenixedu.bennu.notifications.client.exception.NotificationsClientException;
import org.fenixedu.bennu.notifications.client.json.NotificationJson;
import org.fenixedu.notifications.client.backend.NotificationsClientBackend;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;

public class RemoteNotificationsClient implements NotificationsClient {

    private RemoteClientConfig config;
    private static final String NOTIFICATIONS_ENDPOINT = "/api/notifications";
    private Map<String, String> headers;
    private NotificationsClientBackend backend;

    public RemoteNotificationsClient(RemoteClientConfig config, NotificationsClientBackend backend) {
        this.config = config;
        this.headers = new HashMap<>();
        this.headers.put("Content-type", "application/json");
        this.backend = backend;
    }

    private JsonObject getJsonNotification(Notification notification) {
        return NotificationJson.toJson(notification);
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
