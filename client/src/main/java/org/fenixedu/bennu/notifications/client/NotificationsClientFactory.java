package org.fenixedu.bennu.notifications.client;

import org.fenixedu.notifications.client.backend.MemNotificationsClientBackend;
import org.fenixedu.notifications.client.backend.NotificationsClientBackend;

public class NotificationsClientFactory {

    private static final String DEFAULT_FILENAME = "notifications.properties";

    private NotificationsClientFactory() {
    }

    public static NotificationsClient getClientFromPropertiesFile(String filename, NotificationsClientBackend backend) {
        ClientConfig config = ClientConfig.fromPropertiesFile(filename);
        return new RemoteNotificationsClient(config, backend);
    }

    public static NotificationsClient getClientFromPropertiesFile(NotificationsClientBackend backend) {
        return getClientFromPropertiesFile(DEFAULT_FILENAME, backend);
    }

    public static NotificationsClient getClient(String url, String appId, String appSecret, NotificationsClientBackend backend) {
        ClientConfig config = new ClientConfig(url, appId, appSecret);
        return new RemoteNotificationsClient(config, backend);
    }

    public static NotificationsClient getClientFromPropertiesFile(String filename) {
        return getClientFromPropertiesFile(filename, new MemNotificationsClientBackend());
    }

    public static NotificationsClient getClientFromPropertiesFile() {
        return getClientFromPropertiesFile(new MemNotificationsClientBackend());
    }

    public static NotificationsClient getClient(String url, String appId, String appSecret) {
        return getClient(url, appId, appSecret, new MemNotificationsClientBackend());
    }

}
