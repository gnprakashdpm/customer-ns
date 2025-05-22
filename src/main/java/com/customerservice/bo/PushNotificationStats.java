package com.customerservice.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushNotificationStats {
    private int enabled;
    private int disabled;
    private int notificationsReceivedInPeriod;
}

