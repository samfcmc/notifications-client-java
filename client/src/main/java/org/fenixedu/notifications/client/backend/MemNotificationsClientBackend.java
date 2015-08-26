package org.fenixedu.notifications.client.backend;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.fenixedu.bennu.notifications.client.Notification;

public class MemNotificationsClientBackend implements NotificationsClientBackend {

    private List<Notification> notifications;

    public MemNotificationsClientBackend() {
        this.notifications = new LinkedList<>();
    }

    @Override
    public void storePendingNotification(Notification notification) {
        this.notifications.add(notification);
    }

    @Override
    public Collection<Notification> getPendingNotifications() {
        return notifications;
    }

}
