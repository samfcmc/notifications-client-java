package org.fenixedu.bennu.notifications.client.domain;

import com.google.gson.JsonElement;

public class PendingNotification extends PendingNotification_Base {

    public PendingNotification(JsonElement payload, JsonElement usernames) {
        super();
        setPayload(payload);
        setUsernames(usernames);
    }

}
