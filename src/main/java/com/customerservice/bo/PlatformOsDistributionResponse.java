package com.customerservice.bo;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatformOsDistributionResponse {
	private int totalSessions;
	private Map<String, PlatformBreakdown> platformBreakdown;

	public PlatformOsDistributionResponse(int total, PlatformBreakdown mobile, PlatformBreakdown web) {
		this.totalSessions = total;
		this.platformBreakdown = Map.of("mobile", mobile, "web", web);
	}
}
