package com.customerservice.bo;

import lombok.Data;

@Data
public class EnquiryResponse {

	private String message;
	private long enquiryId;
	private String customerId;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getEnquiryId() {
		return enquiryId;
	}
	public void setEnquiryId(long enquiryId) {
		this.enquiryId = enquiryId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}
