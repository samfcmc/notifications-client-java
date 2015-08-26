package org.fenixedu.notifications.client.ff.domain;

import com.google.gson.JsonElement;

public class PendingNotification extends PendingNotification_Base {

    public PendingNotification(JsonElement usernames, JsonElement payload) {
        super();
        setUsernames(usernames);
        setPayload(payload);
        setNotificationsSystem(NotificationsSystem.getInstance());
    }

}
