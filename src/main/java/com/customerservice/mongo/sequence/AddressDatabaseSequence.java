package com.customerservice.mongo.sequence;

import org.springframework.data.annotation.Id;

public class AddressDatabaseSequence {

	@Id
	private String addressId;

	private long seq;

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	@Override
	public String toString() {
		return "AddressDatabaseSequence [addressId=" + addressId + ", seq=" + seq + "]";
	}

}
