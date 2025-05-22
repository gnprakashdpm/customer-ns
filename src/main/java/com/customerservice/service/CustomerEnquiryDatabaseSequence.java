package com.customerservice.service;

import org.springframework.data.annotation.Id;

public class CustomerEnquiryDatabaseSequence {

	@Id
	private String enquiryId;

	private long seq;

	public String getEnquiryId() {
		return enquiryId;
	}

	public void setEnquiryId(String enquiryId) {
		this.enquiryId = enquiryId;
	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	@Override
	public String toString() {
		return "DatabaseSequence [enquiryId=" + enquiryId + ", seq=" + seq + "]";
	}

}
