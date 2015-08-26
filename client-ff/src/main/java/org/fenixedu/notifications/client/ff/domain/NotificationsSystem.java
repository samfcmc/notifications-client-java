package org.fenixedu.notifications.client.ff.domain;

import pt.ist.fenixframework.FenixFramework;

public class NotificationsSystem extends NotificationsSystem_Base {

    private NotificationsSystem() {
        super();
        setRoot(FenixFramework.getDomainRoot());
    }

    public static NotificationsSystem getInstance() {
        if (FenixFramework.getDomainRoot().getNotificationsSystem() == null) {
            new NotificationsSystem();
        }
        return FenixFramework.getDomainRoot().getNotificationsSystem();
    }

}
