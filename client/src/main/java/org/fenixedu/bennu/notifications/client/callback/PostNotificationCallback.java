package org.fenixedu.bennu.notifications.client.callback;

import org.fenixedu.bennu.notifications.client.Notification;
import org.fenixedu.notifications.client.backend.NotificationsClientBackend;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

public class PostNotificationCallback implements Callback<JsonNode> {

    private Notification notification;
    private NotificationsClientBackend backend;

    public PostNotificationCallback(Notification notification, NotificationsClientBackend backend) {
        this.notification = notification;
    }

    @Override
    public void cancelled() {
        // Do nothing...

    }

    @Override
    public void completed(HttpResponse<JsonNode> response) {
        // Do nothing

    }

    @Override
    public void failed(UnirestException exception) {
        backend.storePendingNotification(notification);
    }

}
