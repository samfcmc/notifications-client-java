package org.fenixedu.bennu.notifications.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.fenixedu.bennu.notifications.client.payload.exception.NoDescriptionProvidedException;
import org.fenixedu.bennu.notifications.client.payload.exception.NoTypeProvidedException;
import org.fenixedu.bennu.notifications.client.payload.exception.NoUsernamesProvidedException;

public class Notification {

    private String link;
    private String type;
    private Map<String, String> descriptions;
    private String image;
    private Collection<String> usernames;

    private Notification(Builder builder) {
        // Validate mandatory arguments
        if (builder.descriptions.isEmpty()) {
            throw new NoDescriptionProvidedException();
        }
        if (builder.type == null) {
            throw new NoTypeProvidedException();
        }
        if (builder.usernames.isEmpty()) {
            throw new NoUsernamesProvidedException();
        }

        this.link = builder.link;
        this.type = builder.type;
        this.descriptions = builder.descriptions;
        this.image = builder.image;
        this.usernames = builder.usernames;
    }

    public String getLink() {
        return link;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public Map<String, String> getDescriptions() {
        return descriptions;
    }

    public String getDescription(String language) {
        return descriptions.get(language);
    }

    public Collection<String> getUsernames() {
        return usernames;
    }

    /*
     * Builder
     */
    public static class Builder {

        private Map<String, String> descriptions;
        private String type;
        private Collection<String> usernames;

        //Optional
        private String link;
        private String image;

        public Builder() {
            this.descriptions = new HashMap<>();
            this.usernames = new LinkedList<>();
        }

        public Builder link(String link) {
            this.link = link;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder description(String language, String description) {
            this.descriptions.put(language, description);
            return this;
        }

        public Builder image(String image) {
            this.image = image;
            return this;
        }

        public Builder usernames(String... usernames) {
            for (String username : usernames) {
                this.usernames.add(username);
            }
            return this;
        }

        public Builder usernames(Collection<String> usernames) {
            for (String username : usernames) {
                this.usernames.add(username);
            }
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }
    }

}
