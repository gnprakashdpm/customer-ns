package com.customerservice.bo;

public class ScreenResolution {
    private int width;
    private int height;

    // Default constructor
    public ScreenResolution() {}

    // Parameterized constructor
    public ScreenResolution(int width, int height) {
        this.width = width;
        this.height = height;
    }

    // Getters and Setters
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "ScreenResolution{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
