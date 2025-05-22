package com.customerservice.bo;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatformBreakdown {
	private int total;
	private Map<String, Integer> osBreakdown;
}
