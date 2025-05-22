package com.customerservice.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PushNotificationStatsResponse {
	private int totalMobileUsers;
	private PushNotificationStats mobilePushNotificationStats;
	private int totalWebUsers;
	private PushNotificationStats webPushNotificationStats;

	// Constructor
	public PushNotificationStatsResponse(int totalMobileUsers, PushNotificationStats mobilePushNotificationStats,
			int totalWebUsers, PushNotificationStats webPushNotificationStats) {
		this.totalMobileUsers = totalMobileUsers;
		this.mobilePushNotificationStats = mobilePushNotificationStats;
		this.totalWebUsers = totalWebUsers;
		this.webPushNotificationStats = webPushNotificationStats;
	}

	// Getters and Setters
	public int getTotalMobileUsers() {
		return totalMobileUsers;
	}

	public void setTotalMobileUsers(int totalMobileUsers) {
		this.totalMobileUsers = totalMobileUsers;
	}

	public PushNotificationStats getMobilePushNotificationStats() {
		return mobilePushNotificationStats;
	}

	public void setMobilePushNotificationStats(PushNotificationStats mobilePushNotificationStats) {
		this.mobilePushNotificationStats = mobilePushNotificationStats;
	}

	public int getTotalWebUsers() {
		return totalWebUsers;
	}

	public void setTotalWebUsers(int totalWebUsers) {
		this.totalWebUsers = totalWebUsers;
	}

	public PushNotificationStats getWebPushNotificationStats() {
		return webPushNotificationStats;
	}

	public void setWebPushNotificationStats(PushNotificationStats webPushNotificationStats) {
		this.webPushNotificationStats = webPushNotificationStats;
	}
}
