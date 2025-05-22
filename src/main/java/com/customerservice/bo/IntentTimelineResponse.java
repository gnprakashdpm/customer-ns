package com.customerservice.bo;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntentTimelineResponse {
	private String period;
	private Map<String, Integer> counts;
}
