package org.fenixedu.bennu.notifications.client.domain;

import pt.ist.fenixframework.FenixFramework;

public class NotificationsSystem extends NotificationsSystem_Base {

    private NotificationsSystem() {
        super();
    }

    public static NotificationsSystem getInstance() {
        if (FenixFramework.getDomainRoot().getNotificationsSystem() == null) {
            new NotificationsSystem();
        }
        return FenixFramework.getDomainRoot().getNotificationsSystem();
    }

}
