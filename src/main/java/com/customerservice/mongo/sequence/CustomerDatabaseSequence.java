package com.customerservice.mongo.sequence;

import org.springframework.data.annotation.Id;

public class CustomerDatabaseSequence {

	@Id
	private String customerNumber;

	private long seq;

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	@Override
	public String toString() {
		return "CustomerDatabaseSequence [customerNumber=" + customerNumber + ", seq=" + seq + "]";
	}

}
