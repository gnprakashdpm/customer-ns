package com.customerservice.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.customerservice.bo.AddressInsert;
import com.customerservice.bo.AddressRequest;
import com.customerservice.bo.CustomerConstants;
import com.customerservice.bo.CustomerInsert;
import com.customerservice.bo.CustomerRequest;
import com.customerservice.bo.EnquiryResponse;
import com.customerservice.bo.IntentSummary;
import com.customerservice.bo.LocationStats;
import com.customerservice.bo.LoginInsert;
import com.customerservice.bo.LoginRequest;
import com.customerservice.bo.PlatformOsDistributionResponse;
import com.customerservice.bo.PromptRequest;
import com.customerservice.bo.PushNotificationStatsResponse;
import com.customerservice.bo.QueryAnalyticsResponse;
import com.customerservice.bo.UserRegisterInsert;
import com.customerservice.bo.UserRegisterRequest;
import com.customerservice.entity.Address;
import com.customerservice.entity.Customer;
import com.customerservice.entity.CustomerEnquiries;
import com.customerservice.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller class for customer service.
 * 
 * @author kishore.pyla
 */
@Api(tags = "Customer Services")
@RestController
@RequestMapping("/customer-detail")
public class CustomerServiceController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceController.class);

       @Autowired
       private CustomerService customerService;

	@ApiOperation(value = "getCustomer", notes = "Gets the customer information by key")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/customer/{customerId}")
	public Object getCustomer(@PathVariable Integer customerId) {
		try {
			LOGGER.debug("Get customer request received");
			List<Customer> response = customerService.getCustomer(customerId);
			LOGGER.debug("Get customer request processed");
			if (response == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
						.body("{\"status\" : 404, \"message\": \"" + CustomerConstants.NOT_FOUND_MESSAGE
								+ "\", \"error\": \"" + CustomerConstants.NOT_FOUND_ERROR + "\"}");
			}
			return new ResponseEntity<List<Customer>>(response, HttpStatus.OK);
		} catch (Exception exception) {
			HttpStatus errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			String errorMessage = exception.getMessage();
			if (errorMessage == null || errorMessage.isEmpty()) {
				errorMessage = CustomerConstants.INTERNAL_SERVER_ERROR;
			}

			return ResponseEntity.status(errorStatus).contentType(org.springframework.http.MediaType.APPLICATION_JSON)
					.body("{\"status\": " + errorStatus.value() + ", \"message\": \""
							+ CustomerConstants.INTERNAL_SERVER_ERROR_MESSAGE + "\", \"error\": \"" + errorMessage
							+ "\"}");
		}

	}

	@ApiOperation(value = "addCustomer", notes = "Adds the customer information by key")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/add-customer")
	public Object addCustomer(@RequestBody CustomerRequest requestBody, @RequestParam String emailId,
			@RequestParam Long addressId) {
		try {
			LOGGER.debug("Get customer request received");
			CustomerInsert response = customerService.addCustomer(requestBody, emailId, addressId);
			LOGGER.debug("Get customer request processed");
			if (response == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
						.body("{\"status\" : 404, \"message\": \"" + CustomerConstants.NOT_FOUND_MESSAGE
								+ "\", \"error\": \"" + CustomerConstants.NOT_FOUND_ERROR + "\"}");
			}
			return new ResponseEntity<CustomerInsert>(response, HttpStatus.OK);
		} catch (Exception exception) {
			HttpStatus errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			String errorMessage = exception.getMessage();
			if (errorMessage == null || errorMessage.isEmpty()) {
				errorMessage = CustomerConstants.INTERNAL_SERVER_ERROR;
			}

			return ResponseEntity.status(errorStatus).contentType(org.springframework.http.MediaType.APPLICATION_JSON)
					.body("{\"status\": " + errorStatus.value() + ", \"message\": \""
							+ CustomerConstants.INTERNAL_SERVER_ERROR_MESSAGE + "\", \"error\": \"" + errorMessage
							+ "\"}");
		}

	}

	@ApiOperation(value = "addAddress", notes = "Adds the address information by key")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/add-address")
	public Object addAddress(@RequestBody AddressRequest addressDTO) {
		try {
			LOGGER.debug("Get Address request received");
			AddressInsert response = customerService.addAddress(addressDTO);
			LOGGER.debug("Get Address request processed");
			if (response == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
						.body("{\"status\" : 404, \"message\": \"" + CustomerConstants.NOT_FOUND_MESSAGE
								+ "\", \"error\": \"" + CustomerConstants.NOT_FOUND_ERROR + "\"}");
			}
			return new ResponseEntity<AddressInsert>(response, HttpStatus.OK);
		} catch (Exception exception) {
			HttpStatus errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			String errorMessage = exception.getMessage();
			if (errorMessage == null || errorMessage.isEmpty()) {
				errorMessage = CustomerConstants.INTERNAL_SERVER_ERROR;
			}

			return ResponseEntity.status(errorStatus).contentType(org.springframework.http.MediaType.APPLICATION_JSON)
					.body("{\"status\": " + errorStatus.value() + ", \"message\": \""
							+ CustomerConstants.INTERNAL_SERVER_ERROR_MESSAGE + "\", \"error\": \"" + errorMessage
							+ "\"}");
		}

	}

	// first time user Registration
	@ApiOperation(value = "userRegistration", notes = "Adds the userRegistration")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/user-registration")
	public Object userRegistration(@RequestBody UserRegisterRequest requestBody) {
		try {
			LOGGER.debug("Get customer request received");
			UserRegisterInsert response = customerService.userRegistration(requestBody);
			LOGGER.debug("Get customer request processed");
			if (response == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
						.body("{\"status\" : 404, \"message\": \"" + CustomerConstants.NOT_FOUND_MESSAGE
								+ "\", \"error\": \"" + CustomerConstants.NOT_FOUND_ERROR + "\"}");
			}
			return new ResponseEntity<UserRegisterInsert>(response, HttpStatus.OK);
		} catch (Exception exception) {
			HttpStatus errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			String errorMessage = exception.getMessage();
			if (errorMessage == null || errorMessage.isEmpty()) {
				errorMessage = CustomerConstants.INTERNAL_SERVER_ERROR;
			}

			return ResponseEntity.status(errorStatus).contentType(org.springframework.http.MediaType.APPLICATION_JSON)
					.body("{\"status\": " + errorStatus.value() + ", \"message\": \""
							+ CustomerConstants.INTERNAL_SERVER_ERROR_MESSAGE + "\", \"error\": \"" + errorMessage
							+ "\"}");
		}

	}

	// existing user
	@ApiOperation(value = "existinguserRegistration", notes = "Adds the userRegistration")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
	@CrossOrigin(origins = "*")
	@PostMapping(value = "/exsiting/user/{customerNumber}")
	public Object existinguserRegistration(@PathVariable Long customerNumber, @RequestParam String emailId,
			@RequestBody AddressRequest requestBody) {
		try {
			LOGGER.debug("Get customer request received");
			AddressInsert response = customerService.existinguserRegistration(customerNumber, emailId, requestBody);
			LOGGER.debug("Get customer request processed");
			if (response == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
						.body("{\"status\" : 404, \"message\": \"" + CustomerConstants.NOT_FOUND_MESSAGE
								+ "\", \"error\": \"" + CustomerConstants.NOT_FOUND_ERROR + "\"}");
			}
			return new ResponseEntity<AddressInsert>(response, HttpStatus.OK);
		} catch (Exception exception) {
			HttpStatus errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			String errorMessage = exception.getMessage();
			if (errorMessage == null || errorMessage.isEmpty()) {
				errorMessage = CustomerConstants.INTERNAL_SERVER_ERROR;
			}

			return ResponseEntity.status(errorStatus).contentType(org.springframework.http.MediaType.APPLICATION_JSON)
					.body("{\"status\": " + errorStatus.value() + ", \"message\": \""
							+ CustomerConstants.INTERNAL_SERVER_ERROR_MESSAGE + "\", \"error\": \"" + errorMessage
							+ "\"}");
		}

	}

	@PostMapping(value = "/login")
	public Object login(@RequestBody LoginRequest requestbody) {
		try {
			LOGGER.debug("Get customer request received");
			LoginInsert response = customerService.login(requestbody.getEmailId(), requestbody.getPassword());
			LOGGER.debug("Get customer request processed");
			if (response == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
						.body("{\"status\" : 404, \"message\": \"" + CustomerConstants.NOT_FOUND_MESSAGE
								+ "\", \"error\": \"" + CustomerConstants.NOT_FOUND_ERROR + "\"}");
			}
			return new ResponseEntity<LoginInsert>(response, HttpStatus.OK);
		} catch (Exception exception) {
			HttpStatus errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			String errorMessage = exception.getMessage();
			if (errorMessage == null || errorMessage.isEmpty()) {
				errorMessage = CustomerConstants.INTERNAL_SERVER_ERROR;
			}

			return ResponseEntity.status(errorStatus).contentType(org.springframework.http.MediaType.APPLICATION_JSON)
					.body("{\"status\": " + errorStatus.value() + ", \"message\": \""
							+ CustomerConstants.INTERNAL_SERVER_ERROR_MESSAGE + "\", \"error\": \"" + errorMessage
							+ "\"}");
		}

	}

	@ApiOperation(value = "fetchaddress", notes = "Gets the fetch address information by key")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/fetchaddress/{addressId}")
	public Object fetchaddress(@PathVariable Integer addressId) {
		try {
			LOGGER.debug("Get product request received");
			Address getAddress = customerService.fetchaddress(addressId);
			LOGGER.debug("Get product request processed");
			if (getAddress == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
						.body("{\"status\" : 404, \"message\": \"" + CustomerConstants.NOT_FOUND_MESSAGE
								+ "\", \"error\": \"" + CustomerConstants.NOT_FOUND_ERROR + "\"}");
			}
			return new ResponseEntity<>(getAddress, HttpStatus.OK);
		} catch (Exception exception) {
			HttpStatus errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			String errorMessage = exception.getMessage();
			if (errorMessage == null || errorMessage.isEmpty()) {
				errorMessage = CustomerConstants.INTERNAL_SERVER_ERROR;
			}

			return ResponseEntity.status(errorStatus).contentType(org.springframework.http.MediaType.APPLICATION_JSON)
					.body("{\"status\": " + errorStatus.value() + ", \"message\": \""
							+ CustomerConstants.INTERNAL_SERVER_ERROR_MESSAGE + "\", \"error\": \"" + errorMessage
							+ "\"}");
		}

	}

	@ApiOperation(value = "storeuserPrompt", notes = "Store the propmt for a user based on the prompt request and optional context.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = EnquiryResponse.class),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
	@CrossOrigin(origins = "*")
	@PostMapping("/store/userprompt")
	public ResponseEntity<EnquiryResponse> createOrUpdateEnquiry(@RequestParam String userId,
			@RequestBody PromptRequest promptRequest, @RequestParam(required = false) String whom,
			@RequestParam(required = false) String registered) {

		EnquiryResponse response = customerService.createOrUpdateEnquiry(userId, promptRequest, whom, registered);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/customer-enquiries/intent-timeline")
	public ResponseEntity<Object> getTimeline(@RequestParam String storeCode, @RequestParam String granularity,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		return ResponseEntity.ok(customerService.getIntentTimeline(storeCode, granularity, startDate, endDate));
	}

	@GetMapping("/customer-enquiries/push-notification-stats")
	public ResponseEntity<PushNotificationStatsResponse> getStats(@RequestParam String storeCode,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

		PushNotificationStatsResponse response = customerService.getPushNotificationStats(storeCode, startDate,
				endDate);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/intent-summary")
    public ResponseEntity<List<IntentSummary>> getIntentSummary(
            @RequestParam String storeCode,
            @RequestParam(required = false) String whom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<IntentSummary> summary = customerService.getIntentSummary(storeCode, whom, startDate, endDate);
        return ResponseEntity.ok(summary);

}
	@ApiOperation(value = "getLocationStats", notes = "Retrieve session counts grouped by geographic location (city, country, latitude, longitude), "
			+ "filtered by store code and optionally by date range.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", response = LocationStats.class, responseContainer = "List"),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
	@CrossOrigin(origins = "*")
	@GetMapping("/geo-distribution")
	public ResponseEntity<List<LocationStats>> getLocationStats(@RequestParam String storeCode, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		List<LocationStats> stats = customerService.getLocationStats(storeCode, startDate, endDate);
		if (stats.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(stats);
	}
	@ApiOperation(value = "Get Platform and OS Distribution", notes = "Returns platform-wise and OS-wise session distribution for a given store and date range.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = PlatformOsDistributionResponse.class),
	@ApiResponse(code = 404, message = "Not Found"),@ApiResponse(code = 500, message = "Failure")})
	@CrossOrigin(origins = "*")
	@GetMapping("/customer-enquiries/platform-os-distribution")
	public ResponseEntity<PlatformOsDistributionResponse> getPlatformOsDistribution(
	       @RequestParam String storeCode,
	       @RequestParam(required = false) 
	       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
	       @RequestParam(required = false) 
	       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
	
	   PlatformOsDistributionResponse response = customerService
	           .getPlatformOsDistributionForStore(storeCode, startDate, endDate);
	   return ResponseEntity.ok(response);
	}
	
	@ApiOperation(value = "Analyze Customer Enquiries", notes = "Returns analytics of customer enquiries for a given store and optional date range.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = QueryAnalyticsResponse.class),
			@ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Failure") })
	@CrossOrigin(origins = "*")
	@GetMapping("/query-stats")
	public ResponseEntity<List<QueryAnalyticsResponse>> analyzeEnquiries(
			@RequestParam(required = false) String storeCode,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

		List<QueryAnalyticsResponse> analysis = customerService.analyzeEnquiries(storeCode, startDate, endDate);
		return ResponseEntity.ok(analysis);
	}

}
