package com.customerservice.bo;

public class SessionMetadata {
    private GeoLocation geoLocation;
    private String platform;
    private WebMetadata webMetadata;
    private MobileMetadata mobileMetadata;

    // Default constructor
    public SessionMetadata() {}

    // Parameterized constructor
    public SessionMetadata(GeoLocation geoLocation, String platform, WebMetadata webMetadata, MobileMetadata mobileMetadata) {
        this.geoLocation = geoLocation;
        this.platform = platform;
        this.webMetadata = webMetadata;
        this.mobileMetadata = mobileMetadata;
    }

    // Getters and Setters
    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public WebMetadata getWebMetadata() {
        return webMetadata;
    }

    public void setWebMetadata(WebMetadata webMetadata) {
        this.webMetadata = webMetadata;
    }

    public MobileMetadata getMobileMetadata() {
        return mobileMetadata;
    }

    public void setMobileMetadata(MobileMetadata mobileMetadata) {
        this.mobileMetadata = mobileMetadata;
    }

    @Override
    public String toString() {
        return "SessionMetadata{" +
                "geoLocation=" + geoLocation +
                ", platform='" + platform + '\'' +
                ", webMetadata=" + webMetadata +
                ", mobileMetadata=" + mobileMetadata +
                '}';
    }
}
