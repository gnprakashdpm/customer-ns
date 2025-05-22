package com.customerservice.bo;

public class PushNotifications {
    private boolean enabled;
    private String lastNotificationReceived;

    // Default constructor
    public PushNotifications() {}

    // Parameterized constructor
    public PushNotifications(boolean enabled, String lastNotificationReceived) {
        this.enabled = enabled;
        this.lastNotificationReceived = lastNotificationReceived;
    }

    // Getters and Setters
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getLastNotificationReceived() {
        return lastNotificationReceived;
    }

    public void setLastNotificationReceived(String lastNotificationReceived) {
        this.lastNotificationReceived = lastNotificationReceived;
    }

    @Override
    public String toString() {
        return "PushNotifications{" +
                "enabled=" + enabled +
                ", lastNotificationReceived='" + lastNotificationReceived + '\'' +
                '}';
    }
}

