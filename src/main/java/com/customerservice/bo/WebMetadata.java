package com.customerservice.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebMetadata {
	private String os;
	private String userAgent;
	private String ipAddress;
	private PushNotifications pushNotifications;
	private String appVersion;
	private ScreenResolution screenResolution;

}
