package com.customerservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.customerservice.entity.CustomerEnquiries;

@Repository
public interface CustomerEnquiriesRepository extends MongoRepository<CustomerEnquiries, String> {

	CustomerEnquiries findBySessionIdAndCustomerId(String sessionId, String customerId);

	CustomerEnquiries findByCustomerId(String userId);

	CustomerEnquiries findBySessionId(String userId);

	List<CustomerEnquiries> findByStoreCode(String storeCode);

}
