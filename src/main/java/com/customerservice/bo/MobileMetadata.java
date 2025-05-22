package com.customerservice.bo;

public class MobileMetadata {
    private Device device;
    private String appVersion;
    private PushNotifications pushNotifications;
    private ScreenResolution screenResolution;

    // Default constructor
    public MobileMetadata() {}

    // Parameterized constructor
    public MobileMetadata(Device device, String appVersion, PushNotifications pushNotifications, ScreenResolution screenResolution) {
        this.device = device;
        this.appVersion = appVersion;
        this.pushNotifications = pushNotifications;
        this.screenResolution = screenResolution;
    }

    // Getters and Setters
    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public PushNotifications getPushNotifications() {
        return pushNotifications;
    }

    public void setPushNotifications(PushNotifications pushNotifications) {
        this.pushNotifications = pushNotifications;
    }

    public ScreenResolution getScreenResolution() {
        return screenResolution;
    }

    public void setScreenResolution(ScreenResolution screenResolution) {
        this.screenResolution = screenResolution;
    }

    @Override
    public String toString() {
        return "MobileMetadata{" +
                "device=" + device +
                ", appVersion='" + appVersion + '\'' +
                ", pushNotifications=" + pushNotifications +
                ", screenResolution=" + screenResolution +
                '}';
    }
}
