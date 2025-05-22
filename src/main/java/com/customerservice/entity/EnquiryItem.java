package com.customerservice.entity;

import java.util.List;

import lombok.Data;

@Data
public class EnquiryItem {

	private String timestamp; // e.g., "2025-01-17T10:15:00Z"
	private String queryText; // e.g., "What is the price of the latest iPhone?"
	private String mainResponse;         // nullable
	private String keywords;             // nullable
	private String potentialQuestions;
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getQueryText() {
		return queryText;
	}
	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}

}
