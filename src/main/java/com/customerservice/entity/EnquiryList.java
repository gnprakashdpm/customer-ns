package com.customerservice.entity;

import java.util.List;

import lombok.Data;

@Data
public class EnquiryList {

	private String whom;
	private List<EnquiryItem> enquiries;
	public String getWhom() {
		return whom;
	}
	public void setWhom(String whom) {
		this.whom = whom;
	}
	public List<EnquiryItem> getEnquiries() {
		return enquiries;
	}
	public void setEnquiries(List<EnquiryItem> enquiries) {
		this.enquiries = enquiries;
	}

}
