package org.fenixedu.bennu.notifications.client.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.fenixedu.bennu.notifications.client.Notification;
import org.fenixedu.bennu.notifications.client.payload.exception.NoDescriptionProvidedException;
import org.fenixedu.bennu.notifications.client.payload.exception.NoTypeProvidedException;
import org.fenixedu.bennu.notifications.client.payload.exception.NoUsernamesProvidedException;
import org.junit.Test;

public class PayloadBuilderTest {
    private static final String LANGUAGE_EN = "en";
    private static final String DESCRIPTION = "description";
    private static final String TYPE = "NOTIFICATION_TYPE";
    private static final String IMAGE = "image";
    private static final String LINK = "link";
    private static final String USERNAME1 = "username1";
    private static final String USERNAME2 = "username2";
    private static final String USERNAME3 = "username3";

    @Test
    public void success() {
        Notification.Builder builder = new Notification.Builder();
        builder.description(LANGUAGE_EN, DESCRIPTION).type(TYPE).image(IMAGE).link(LINK)
                .usernames(USERNAME1, USERNAME2, USERNAME3);
        Notification payload = builder.build();

        assertEquals("payload does not have the expected description for language EN", payload.getDescription(LANGUAGE_EN),
                DESCRIPTION);
        assertEquals("payload does not have the expected type", payload.getType(), TYPE);
        assertEquals("payload does not have the expected image", payload.getImage(), IMAGE);
        assertEquals("payload does not have the expected link", payload.getLink(), LINK);
    }

    @Test
    public void withoutImage() {
        Notification.Builder builder = new Notification.Builder();
        builder.description(LANGUAGE_EN, DESCRIPTION).type(TYPE).link(LINK).usernames(USERNAME1, USERNAME2, USERNAME3);
        Notification payload = builder.build();

        assertEquals("payload does not have the expected description for language EN", payload.getDescription(LANGUAGE_EN),
                DESCRIPTION);
        assertEquals("payload does not have the expected type", payload.getType(), TYPE);
        assertNull("payload have image", payload.getImage());
        assertEquals("payload does not have the expected link", payload.getLink(), LINK);
    }

    @Test
    public void withoutLink() {
        Notification.Builder builder = new Notification.Builder();
        builder.description(LANGUAGE_EN, DESCRIPTION).type(TYPE).image(IMAGE).usernames(USERNAME1, USERNAME2, USERNAME3);
        Notification payload = builder.build();

        assertEquals("payload does not have the expected description for language EN", payload.getDescription(LANGUAGE_EN),
                DESCRIPTION);
        assertEquals("payload does not have the expected type", payload.getType(), TYPE);
        assertNull("payload have link", payload.getLink());
        assertEquals("payload does not have the expected image", payload.getImage(), IMAGE);
    }

    @Test(expected = NoTypeProvidedException.class)
    public void withoutType() {
        Notification.Builder builder = new Notification.Builder();
        builder.description(LANGUAGE_EN, DESCRIPTION).link(LINK).usernames(USERNAME1, USERNAME2, USERNAME3).build();
    }

    @Test(expected = NoDescriptionProvidedException.class)
    public void withoutDescription() {
        Notification.Builder builder = new Notification.Builder();
        builder.type(TYPE).image(IMAGE).link(LINK).usernames(USERNAME1, USERNAME2, USERNAME3).build();
    }

    @Test(expected = NoUsernamesProvidedException.class)
    public void noUsernamesProvided() {
        Notification.Builder builder = new Notification.Builder();
        builder.type(TYPE).image(IMAGE).link(LINK).description(LANGUAGE_EN, DESCRIPTION).build();
    }

}
