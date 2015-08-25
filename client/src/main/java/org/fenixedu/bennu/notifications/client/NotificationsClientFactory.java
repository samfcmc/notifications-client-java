package org.fenixedu.bennu.notifications.client;

public class NotificationsClientFactory {

    private static final String DEFAULT_FILENAME = "notifications.properties";

    private NotificationsClientFactory() {
    }

    public static NotificationsClient getClientFromPropertiesFile(String filename) {
        RemoteClientConfig config = RemoteClientConfig.fromPropertiesFile(filename);
        return new RemoteNotificationsClient(config);
    }

    public static NotificationsClient getClientFromPropertiesFile() {
        return getClientFromPropertiesFile(DEFAULT_FILENAME);
    }

    public static NotificationsClient getClient(String url, String appId, String appSecret) {
        RemoteClientConfig config = new RemoteClientConfig(url, appId, appSecret);
        return new RemoteNotificationsClient(config);
    }

}
