package org.fenixedu.bennu.notifications.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.fenixedu.bennu.notifications.client.exception.ErrorReadingConfigurationFileException;
import org.fenixedu.bennu.notifications.client.exception.PropertiesFileNotFound;

public class ClientConfig {

    private String url;
    private String appId;
    private String appSecret;

    public ClientConfig(String url, String appId, String appSecret) {
        this.url = url;
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public String getUrl() {
        return url;
    }

    public String getEndpointUrl(String endpoint) {
        return getUrl() + endpoint;
    }

    public String getAppId() {
        return appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public static ClientConfig fromPropertiesFile(String filename) {
        Properties properties = new Properties();
        InputStream inputStream = ClientConfig.class.getClassLoader().getResourceAsStream(filename);
        if (inputStream == null) {
            throw new PropertiesFileNotFound(filename);
        } else {
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                throw new ErrorReadingConfigurationFileException();
            }
            String url = properties.getProperty("url");
            String appId = properties.getProperty("appId");
            String appSecret = properties.getProperty("appSecret");
            return new ClientConfig(url, appId, appSecret);
        }
    }
}
