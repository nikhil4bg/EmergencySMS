package com.praxisgs.emergencysms.eventbus;

/**
 * Created on 10/03/2016.
 */
public class SnackBarEvents {

    public static class EventInformation {
        private final int messageId;
        private final String[] parameters;

        public EventInformation(int messageResId, String... parameters) {
            this.messageId = messageResId;
            this.parameters = parameters;
        }

        public int getMessageId() {
            return messageId;
        }

        public String[] getParameters() {
            return parameters;
        }
    }

    public static class EventError {
        private final int messageId;
        private final String[] parameters;

        public EventError(int messageResId, String... parameters) {
            this.messageId = messageResId;
            this.parameters = parameters;
        }

        public int getMessageId() {
            return messageId;
        }

        public String[] getParameters() {
            return parameters;
        }
    }
}
