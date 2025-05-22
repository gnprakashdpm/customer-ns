package com.customerservice.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.customerservice.bo.CustomerInsert;
import com.customerservice.bo.AddressRequest;
import com.customerservice.bo.AddressInsert;
import com.customerservice.bo.CustomerRequest;
import com.customerservice.bo.EnquiryResponse;
import com.customerservice.bo.IntentSummary;
import com.customerservice.bo.LocationStats;
import com.customerservice.bo.LoginInsert;
import com.customerservice.bo.PlatformOsDistributionResponse;
import com.customerservice.bo.PromptRequest;
import com.customerservice.bo.PushNotificationStatsResponse;
import com.customerservice.bo.QueryAnalyticsResponse;
import com.customerservice.bo.UserRegisterInsert;
import com.customerservice.bo.UserRegisterRequest;
import com.customerservice.entity.Address;
import com.customerservice.entity.Customer;
import com.customerservice.entity.CustomerEnquiries;

public interface CustomerService {
	public List<Customer> getCustomer(Integer customerId);

	public AddressInsert addAddress(AddressRequest addressDTO);

	public CustomerInsert addCustomer(CustomerRequest requestBody, String emailId, Long addressId);

	public UserRegisterInsert userRegistration(UserRegisterRequest requestBody);

	public AddressInsert existinguserRegistration(Long customerNumber, String emailId, AddressRequest requestBody);

	public LoginInsert login(String emailId, String password);

	public Address fetchaddress(Integer addressId);

	public EnquiryResponse createOrUpdateEnquiry(String userId, PromptRequest promptRequest, String whom,
			String registered);

	public Object getIntentTimeline(String storeCode, String granularity, LocalDate startDate, LocalDate endDate);

	public PushNotificationStatsResponse getPushNotificationStats(String storeCode, LocalDate startDate,
			LocalDate endDate);
	public List<IntentSummary> getIntentSummary(String storeCode, String whom, LocalDate startDate, LocalDate endDate);
	
	public List<LocationStats> getLocationStats(String storeCode, LocalDate startDate, LocalDate endDate);
	
	public PlatformOsDistributionResponse getPlatformOsDistributionForStore(String storeCode, LocalDate startDate, LocalDate endDate);
	
        public List<QueryAnalyticsResponse> analyzeEnquiries(List<CustomerEnquiries> enquiries, String storeCode,
                        LocalDate startDate, LocalDate endDate);

         public List<QueryAnalyticsResponse> analyzeEnquiries(String storeCode, LocalDate startDate, LocalDate endDate);

       /**
        * Returns customers who haven't logged in within the specified number of days.
        */
       List<Customer> getInactiveCustomers(int days);
}

