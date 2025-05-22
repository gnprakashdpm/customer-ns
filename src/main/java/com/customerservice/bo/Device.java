package com.customerservice.bo;

public class Device {
    private String model;
    private String os;

    // Default constructor
    public Device() {}

    // Parameterized constructor
    public Device(String model, String os) {
        this.model = model;
        this.os = os;
    }

    // Getters and Setters
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    @Override
    public String toString() {
        return "Device{" +
                "model='" + model + '\'' +
                ", os='" + os + '\'' +
                '}';
    }
}
