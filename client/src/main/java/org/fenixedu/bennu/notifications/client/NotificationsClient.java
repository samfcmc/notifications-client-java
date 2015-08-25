package org.fenixedu.bennu.notifications.client;

import java.util.Collection;

import org.fenixedu.bennu.notifications.client.payload.NotificationPayload;

public interface NotificationsClient {
    public void postNotification(String username, NotificationPayload payload);

    public void postNotification(Collection<String> usernames, NotificationPayload payload);

}
