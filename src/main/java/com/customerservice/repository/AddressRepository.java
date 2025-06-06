package com.customerservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.customerservice.entity.Address;

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {

	Optional<Address> findByaddressId(Integer addressId);

}
