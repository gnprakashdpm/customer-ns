package com.customerservice.bo;

public class LocationStats {
	private String city;
	private String country;
	private double latitude;
	private double longitude;
	private long sessionCount;
	private LocationPlatformBreakdown platformBreakdown;

	public LocationStats() {
	}

	public LocationStats(String city, String country, double latitude, double longitude, long sessionCount,
			LocationPlatformBreakdown platformBreakdown) {
		this.city = city;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
		this.sessionCount = sessionCount;
		this.platformBreakdown = platformBreakdown;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public long getSessionCount() {
		return sessionCount;
	}

	public void setSessionCount(long sessionCount) {
		this.sessionCount = sessionCount;
	}

	public LocationPlatformBreakdown getPlatformBreakdown() {
		return platformBreakdown;
	}

	public void setPlatformBreakdown(LocationPlatformBreakdown platformBreakdown) {
		this.platformBreakdown = platformBreakdown;
	}

	// getters + setters omitted for brevity
}
