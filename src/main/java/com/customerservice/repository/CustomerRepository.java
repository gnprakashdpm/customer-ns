package com.customerservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.customerservice.entity.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

	List<Customer> findByCustomerNumber(Integer customerId);

	Optional<Customer> findByEmailId(String emailId);

}
