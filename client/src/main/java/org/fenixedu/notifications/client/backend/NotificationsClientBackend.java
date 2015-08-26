package org.fenixedu.notifications.client.backend;

import java.util.Collection;

import org.fenixedu.bennu.notifications.client.Notification;

public interface NotificationsClientBackend {

    void storePendingNotification(Notification notification);

    Collection<Notification> getPendingNotifications();

}
