package org.fenixedu.bennu.notifications.client.payload.exception;

public class NoUsernamesProvidedException extends NotificationPayloadException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "Not a single username was provided for a new notification";

    public NoUsernamesProvidedException() {
        super(MESSAGE);
    }

}
