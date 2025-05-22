package com.customerservice.entity;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.customerservice.bo.SessionMetadata;

import lombok.Data;

@Data
@Document(collection = "CustomerEnquiries")
public class CustomerEnquiries {

	public static final String SEQUENCE_NAME = "customerEnquir_sequence";

	private String id;
	private long enquiryId;
	private String sessionId;
	private String customerId;
	private List<EnquiryList> enquiryList;
	private String storeCode;
	private SessionMetadata sessionMetadata;
//	private Instant createdAt; // Created timestamp
//	private Instant updatedAt;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getEnquiryId() {
		return enquiryId;
	}
	public void setEnquiryId(long enquiryId) {
		this.enquiryId = enquiryId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public List<EnquiryList> getEnquiryList() {
		return enquiryList;
	}
	public void setEnquiryList(List<EnquiryList> enquiryList) {
		this.enquiryList = enquiryList;
	}
	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public SessionMetadata getSessionMetadata() {
		return sessionMetadata;
	}
	public void setSessionMetadata(SessionMetadata sessionMetadata) {
		this.sessionMetadata = sessionMetadata;
	}

}
