package com.customerservice.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.customerservice.bo.AddressInsert;
import com.customerservice.bo.AddressRequest;
import com.customerservice.bo.CustomerInsert;
import com.customerservice.bo.CustomerRequest;
import com.customerservice.bo.CustomerType;
import com.customerservice.bo.EnquiryResponse;
import com.customerservice.bo.FunctionalityDetail;
import com.customerservice.bo.GeoLocation;
import com.customerservice.bo.IntentSummary;
import com.customerservice.bo.IntentTimelineResponse;
import com.customerservice.bo.LocationPlatformBreakdown;
import com.customerservice.bo.LocationStats;
import com.customerservice.bo.LoginInsert;
import com.customerservice.bo.PlatformBreakdown;
import com.customerservice.bo.PlatformOsDistributionResponse;
import com.customerservice.bo.PromptRequest;
import com.customerservice.bo.PushNotificationStats;
import com.customerservice.bo.PushNotificationStatsResponse;
import com.customerservice.bo.PushNotifications;
import com.customerservice.bo.QnA;
import com.customerservice.bo.QueryAnalyticsResponse;
import com.customerservice.bo.SessionMetadata;
import com.customerservice.bo.UserQueryDetail;
import com.customerservice.bo.UserRegisterInsert;
import com.customerservice.bo.UserRegisterRequest;
import com.customerservice.bo.WebMetadata;
import com.customerservice.entity.Address;
import com.customerservice.entity.Customer;
import com.customerservice.entity.CustomerEnquiries;
import com.customerservice.entity.EnquiryItem;
import com.customerservice.entity.EnquiryList;
import com.customerservice.mongo.sequence.AddressSequenceGeneratorService;
import com.customerservice.mongo.sequence.CustomerSequenceGeneratorService;
import com.customerservice.repository.AddressRepository;
import com.customerservice.repository.CustomerEnquiriesRepository;
import com.customerservice.repository.CustomerRepository;

@Component
public class CustomerServiceImpl implements CustomerService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	AddressRepository addressRepo;

	@Autowired
	AddressSequenceGeneratorService addressseqGeneratorService;

	@Autowired
	CustomerSequenceGeneratorService customerseqGeneratorService;

	@Autowired
	private CustomerEnquiriesRepository customerEnquiriesRepo;

	@Autowired
	CustomerEnquirySequenceGeneratorService seqGeneratorService;

	public List<Customer> getCustomer(Integer customerId) {
		List<Customer> customerResponse = new ArrayList<>();
		try {

			customerResponse = customerRepo.findByCustomerNumber(customerId);

		} catch (Exception exception) {
			logger.error("Error occurred while fetching customer information. Error: " + exception.getMessage(),
					exception);
			throw new RuntimeException(exception);
		}
		return customerResponse;
	}

	@Override
	public CustomerInsert addCustomer(CustomerRequest requestBody, String emailId, Long addressId) {
		CustomerInsert response = new CustomerInsert();
		try {

			Optional<Customer> existingCustomerOpt = customerRepo.findByEmailId(emailId);

			if (existingCustomerOpt.isPresent()) {

				Customer existingCustomer = existingCustomerOpt.get();
				existingCustomer.setAddressId(addressId);
				Customer updatedCustomer = customerRepo.save(existingCustomer);

				response.setSuccess("Customer updated successfully.");
				response.setCustomerNumber(updatedCustomer.getCustomerNumber());
				response.setFirstName(updatedCustomer.getFirstName());
				response.setLastName(updatedCustomer.getLastName());
				response.setEmailId(emailId);
				response.setPhoneNumber(updatedCustomer.getPhoneNumber());
				response.setCustomerType(updatedCustomer.getCustomerType());
				response.setAddressId(addressId);
				logger.info("Customer updated successfully.");
			} else {

				Customer customer = new Customer();

				customer.setCustomerNumber(customerseqGeneratorService.generateSequence(Customer.SEQUENCE_NAME));
				customer.setFirstName(requestBody.getFirstName());
				customer.setLastName(requestBody.getLastName());
				customer.setEmailId(emailId);
				customer.setPhoneNumber(requestBody.getPhoneNumber());
				customer.setCustomerType(CustomerType.GUEST);
				customer.setAddressId(addressId);
				Customer savedCustomer = customerRepo.save(customer);

				if (savedCustomer != null) {
					response.setSuccess("Customer inserted successfully.");
					response.setCustomerNumber(savedCustomer.getCustomerNumber());
					response.setFirstName(savedCustomer.getFirstName());
					response.setLastName(savedCustomer.getLastName());
					response.setEmailId(savedCustomer.getEmailId());
					response.setPhoneNumber(savedCustomer.getPhoneNumber());
					response.setCustomerType(savedCustomer.getCustomerType());
					response.setAddressId(addressId);
					logger.info("Customer inserted successfully.");
				} else {
					logger.error("Failed to insert customer.");
				}
			}
		} catch (Exception exception) {
			logger.error("Error occurred while processing customer information. Error: " + exception.getMessage(),
					exception);
			throw new RuntimeException(exception);
		}
		return response;
	}

	@Override
	public AddressInsert addAddress(@RequestBody AddressRequest addressDTO) {
		AddressInsert response = new AddressInsert();
		try {

			Address address = new Address();
			address.setAddressId(addressseqGeneratorService.generateSequence(Address.SEQUENCE_NAME));
			address.setAddressLine1(addressDTO.getAddressLine1());
			address.setAddressLine2(addressDTO.getAddressLine2());
			address.setCity(addressDTO.getCity());
			address.setState(addressDTO.getState());
			address.setPostalCode(addressDTO.getPostalCode());
			address.setCountry(addressDTO.getCountry());
			address.setStreet(addressDTO.getStreet());
			Address savedAddress = addressRepo.save(address);

			if (savedAddress != null) {
				response.setAddressId(savedAddress.getAddressId());
				response.setAddressLine1(savedAddress.getAddressLine1());
				response.setAddressLine2(savedAddress.getAddressLine2());
				response.setCity(savedAddress.getCity());
				response.setState(savedAddress.getState());
				response.setPostalCode(savedAddress.getPostalCode());
				response.setCountry(savedAddress.getCountry());
				response.setStreet(savedAddress.getStreet());
				response.setSuccess("Address inserted successfully.");
				logger.info("Address inserted successfully.");
			} else {
				logger.error("Failed to insert address.");
			}

		} catch (Exception exception) {
			logger.error("Error occurred while fetching customer information. Error: " + exception.getMessage(),
					exception);
			throw new RuntimeException(exception);
		}
		return response;
	}

	@Override
	public UserRegisterInsert userRegistration(UserRegisterRequest requestBody) {

		UserRegisterInsert response = new UserRegisterInsert();
		try {

			Customer userRegister = new Customer();
			userRegister.setCustomerNumber(customerseqGeneratorService.generateSequence(Customer.SEQUENCE_NAME));
			userRegister.setEmailId(requestBody.getEmailId());
			userRegister.setPassword(passwordEncoder.encode(requestBody.getPassword()));
			userRegister.setFirstName(requestBody.getFirstName());
			userRegister.setLastName(requestBody.getLastName());
			userRegister.setCustomerType(CustomerType.REGISTERED);

			Customer savedUserRegister = customerRepo.save(userRegister);

			if (savedUserRegister != null) {
				response.setCustomerNumber(savedUserRegister.getCustomerNumber());
				response.setSuccess("User Register successfully.");
				response.setUserId(savedUserRegister.getEmailId());
				response.setPassword(passwordEncoder.encode(savedUserRegister.getPassword()));
				response.setFirstName(savedUserRegister.getFirstName());
				response.setLastName(savedUserRegister.getLastName());
				response.setCustomerType(savedUserRegister.getCustomerType());

				logger.info("User Register successfully.");
			} else {
				logger.error("Failed to User Register.");
			}

		} catch (Exception exception) {
			logger.error("Error occurred while fetching user register information. Error: " + exception.getMessage(),
					exception);
			throw new RuntimeException(exception);
		}
		return response;
	}

	@Override
	public AddressInsert existinguserRegistration(Long customerNumber, String emailId, AddressRequest requestBody) {

		AddressInsert response = new AddressInsert();
		try {

			Optional<Customer> existingCustomerOpt = customerRepo.findByEmailId(emailId);

			if (existingCustomerOpt.isPresent()) {
				Customer existingCustomer = existingCustomerOpt.get();

				Address address = new Address();
				address.setAddressId(addressseqGeneratorService.generateSequence(Address.SEQUENCE_NAME));
				address.setAddressLine1(requestBody.getAddressLine1());
				address.setAddressLine2(requestBody.getAddressLine2());
				address.setCity(requestBody.getCity());
				address.setState(requestBody.getState());
				address.setPostalCode(requestBody.getPostalCode());
				address.setCountry(requestBody.getCountry());
				address.setStreet(requestBody.getStreet());
				address.setAddressType(requestBody.getAddressType());

				address.setCustomerNumber(customerNumber);

				Address saveaddress = addressRepo.save(address);

				existingCustomer.setAddressId(address.getAddressId());
				customerRepo.save(existingCustomerOpt.get());

				if (saveaddress != null) {
					response.setCustomerNumber(customerNumber);
					response.setSuccess("Existing User address added successfully");
					response.setAddressId(saveaddress.getAddressId());
					response.setAddressLine1(saveaddress.getAddressLine1());
					response.setAddressLine2(saveaddress.getAddressLine2());
					response.setCity(saveaddress.getCity());
					response.setState(saveaddress.getState());
					response.setPostalCode(saveaddress.getPostalCode());
					response.setCountry(saveaddress.getCountry());
					response.setStreet(saveaddress.getStreet());
					response.setAddressType(saveaddress.getAddressType());

					logger.info("Existing User address added successfully");
				} else {
					logger.error("Failed to existing User Register.");
				}
			}

		} catch (Exception exception) {
			logger.error("Error occurred while fetching user register information. Error: " + exception.getMessage(),
					exception);
			throw new RuntimeException(exception);
		}
		return response;
	}

	@Override
	public LoginInsert login(String emailId, String password) {
		LoginInsert response = new LoginInsert();
		try {

			Optional<Customer> userlogin = customerRepo.findByEmailId(emailId);

			if (!userlogin.isPresent()) {
				throw new RuntimeException("UserId not found.");
			}

			Customer existingUser = userlogin.get();

                        if (passwordEncoder.matches(password, existingUser.getPassword())) {

                                response.setCustomerNumber(existingUser.getCustomerNumber());
                                response.setEmailId(existingUser.getEmailId());
                                response.setPhoneNumber(existingUser.getPhoneNumber());
                                response.setPassword(existingUser.getPassword());
                                response.setMessage("Login successful.");
                                // update last login timestamp
                                existingUser.setLastLogin(Instant.now().toString());
                                customerRepo.save(existingUser);
                                logger.info("Login successful.");
			} else {

				response.setMessage("Invalid credentials");
				logger.error("Invalid credentials");
				throw new RuntimeException("Invalid credentials.");
			}

		} catch (Exception exception) {
			logger.error("Error occurred while fetching login details. Error: " + exception.getMessage(), exception);
			throw new RuntimeException(exception);
		}
		return response;
	}

	@Override
	public Address fetchaddress(Integer addressId) {
		try {
			Optional<Address> productOpt = addressRepo.findByaddressId(addressId);
			if (!productOpt.isPresent()) {
				throw new RuntimeException("Address not found.");
			}
			return productOpt.get();
		} catch (RuntimeException e) {
			logger.error("Error fetching Address with ID {}: {}", addressId, e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error("An unexpected error occurred while fetching Address with ID {}: {}", addressId,
					e.getMessage(), e);
			throw new RuntimeException("An unexpected error occurred. Please try again later.", e);
		}
	}

	@Override
	public EnquiryResponse createOrUpdateEnquiry(String userId, PromptRequest promptRequest, String whom,
			String registered) {
		EnquiryResponse response = new EnquiryResponse();

		try {
			CustomerEnquiries existingEnquiry = null;
			if ("true".equalsIgnoreCase(registered)) {
				existingEnquiry = customerEnquiriesRepo.findByCustomerId(userId);
			} else {
				existingEnquiry = customerEnquiriesRepo.findBySessionId(userId);
			}

			if (existingEnquiry != null) {
				EnquiryList existingEnquiryList = null;

				for (EnquiryList list : existingEnquiry.getEnquiryList()) {
					if (list.getWhom().equals(whom)) {
						existingEnquiryList = list;
						break;
					}
				}

				// If 'whom' doesn't exist, create a new list
				if (existingEnquiryList == null) {
					existingEnquiryList = new EnquiryList();
					existingEnquiryList.setWhom(whom);
					existingEnquiryList.setEnquiries(new ArrayList<>());
					existingEnquiry.getEnquiryList().add(existingEnquiryList);
				}

				// Create a new enquiry item
				EnquiryItem enquiryItem = new EnquiryItem();
				enquiryItem.setTimestamp(Instant.now().toString());
				enquiryItem.setQueryText(promptRequest.getPrompt());

				// Add the new enquiry item to the existing list
				existingEnquiryList.getEnquiries().add(enquiryItem);

				// Save the updated enquiry to the database
				customerEnquiriesRepo.save(existingEnquiry);

				response.setMessage("Enquiry updated successfully.");
				response.setEnquiryId(existingEnquiry.getEnquiryId());
				response.setCustomerId(existingEnquiry.getCustomerId());
			} else {
				// If no existing enquiry is found, create a new enquiry
				CustomerEnquiries newEnquiry = new CustomerEnquiries();
				newEnquiry.setEnquiryId(seqGeneratorService.generateSequence(CustomerEnquiries.SEQUENCE_NAME));

				// Set customerId or sessionId based on registration status
				if ("true".equalsIgnoreCase(registered)) {
					newEnquiry.setCustomerId(userId);
				} else {
					newEnquiry.setSessionId(userId);
				}

				// Create a new enquiry list and item
				EnquiryList newEnquiryList = new EnquiryList();
				newEnquiryList.setWhom(whom);

				EnquiryItem newEnquiryItem = new EnquiryItem();
				newEnquiryItem.setTimestamp(Instant.now().toString()); // Store the current timestamp
				newEnquiryItem.setQueryText(promptRequest.getPrompt()); // Store the user's prompt directly

				// Add the new enquiry item to the list
				newEnquiryList.setEnquiries(Arrays.asList(newEnquiryItem));

				// Add the enquiry list to the new enquiry
				newEnquiry.setEnquiryList(Arrays.asList(newEnquiryList));
				newEnquiry.setStoreCode(promptRequest.getStoreCode());
				newEnquiry.setSessionMetadata(promptRequest.getSessionMetadata());

				// Save the new enquiry to the database
				customerEnquiriesRepo.save(newEnquiry);

				response.setMessage("Enquiry created successfully.");
				response.setEnquiryId(newEnquiry.getEnquiryId());
				response.setCustomerId(newEnquiry.getCustomerId());
			}

		} catch (Exception e) {
			logger.error("Error occurred while processing the enquiry: " + e.getMessage(), e);
			response.setMessage("Error occurred while processing the enquiry.");
		}

		return response;
	}

	@Override
	public List<IntentTimelineResponse> getIntentTimeline(String storeCode, String granularity, LocalDate start,
			LocalDate end) {
		List<CustomerEnquiries> data = customerEnquiriesRepo.findByStoreCode(storeCode);

		Map<String, Map<String, Integer>> aggregated = new TreeMap<>();
		Set<String> allWhomTypes = new HashSet<>();

		for (CustomerEnquiries ce : data) {
			if (ce.getEnquiryList() == null)
				continue;

			for (EnquiryList list : ce.getEnquiryList()) {
				String whom = normalizeWhom(list.getWhom());
				allWhomTypes.add(whom); // Collect all types

				if (list.getEnquiries() == null)
					continue;

				for (EnquiryItem item : list.getEnquiries()) {
					String ts = item.getTimestamp();
					if (ts == null || ts.isEmpty())
						continue;

					Instant instant;
					try {
						instant = Instant.parse(ts);
					} catch (Exception e) {
						continue;
					}

					LocalDate date = instant.atZone(ZoneOffset.UTC).toLocalDate();
					if ((start != null && date.isBefore(start)) || (end != null && date.isAfter(end))) {
						continue;
					}

					String periodKey = getPeriodKey(date, granularity);
					if (periodKey == null)
						continue;

					aggregated.computeIfAbsent(periodKey, k -> new HashMap<>());
					Map<String, Integer> counts = aggregated.get(periodKey);
					counts.put(whom, counts.getOrDefault(whom, 0) + 1);
				}
			}
		}

		// Generate all possible periods in the date range
		List<String> allPeriods = generateAllPeriodsInRange(start, end, granularity);

		// Ensure all periods are represented in the final result
		for (String period : allPeriods) {
			aggregated.computeIfAbsent(period, k -> new HashMap<>());
			Map<String, Integer> counts = aggregated.get(period);
			for (String whom : allWhomTypes) {
				counts.putIfAbsent(whom, 0);
			}
		}

		return aggregated.entrySet().stream().map(e -> new IntentTimelineResponse(e.getKey(), e.getValue()))
				.collect(Collectors.toList());
	}

	private List<String> generateAllPeriodsInRange(LocalDate start, LocalDate end, String granularity) {
		List<String> periods = new ArrayList<>();
		LocalDate date = start;

		while (!date.isAfter(end)) {
			String periodKey = getPeriodKey(date, granularity);
			if (periodKey != null && !periods.contains(periodKey)) {
				periods.add(periodKey);
			}

			// Advance by correct step depending on granularity
			switch (granularity.toLowerCase()) {
			case "day":
				date = date.plusDays(1);
				break;
			case "week":
				date = date.plusWeeks(1);
				break;
			case "month":
				date = date.plusMonths(1);
				break;
			default:
				throw new IllegalArgumentException("Unsupported granularity: " + granularity);
			}
		}

		return periods;
	}

	private String getPeriodKey(LocalDate date, String granularity) {
		switch (granularity.toLowerCase()) {
		case "day":
			return date.toString(); // e.g. 2025-03-28
		case "week":
			WeekFields weekFields = WeekFields.ISO;
			int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
			int weekYear = date.get(weekFields.weekBasedYear());
			return String.format("%d-W%02d", weekYear, weekNumber); // e.g. 2025-W13
		case "month":
			return String.format("%d-%02d", date.getYear(), date.getMonthValue()); // e.g. 2025-03
		default:
			return null;
		}
	}

	private String normalizeWhom(String whom) {
		return whom == null ? "unknown" : whom.toLowerCase().trim();
	}

	@Override
	public List<IntentSummary> getIntentSummary(String storeCode, String whom, LocalDate startDate, LocalDate endDate) {
		List<CustomerEnquiries> data = customerEnquiriesRepo.findByStoreCode(storeCode);
		Map<String, IntentSummary> summaryMap = new HashMap<>();

		for (CustomerEnquiries ce : data) {
			if (ce.getEnquiryList() == null)
				continue;

			for (EnquiryList list : ce.getEnquiryList()) {
				String currentWhom = normalizeWhom(list.getWhom());

				if (whom != null && !whom.isEmpty() && !currentWhom.equalsIgnoreCase(whom))
					continue;
				if (list.getEnquiries() == null)
					continue;

				for (EnquiryItem item : list.getEnquiries()) {
					String ts = item.getTimestamp();
					if (ts == null || ts.isEmpty())
						continue;

					Instant instant;
					try {
						instant = Instant.parse(ts);
					} catch (Exception e) {
						continue;
					}

					LocalDate date = instant.atZone(ZoneOffset.UTC).toLocalDate();
					if ((startDate != null && date.isBefore(startDate)) || (endDate != null && date.isAfter(endDate))) {
						continue;
					}

					IntentSummary summary = summaryMap.computeIfAbsent(currentWhom, k -> {
						IntentSummary s = new IntentSummary();
						s.setWhom(currentWhom);
						s.setQueries(new ArrayList<>());
						return s;
					});

					summary.getQueries().add(item.getQueryText());
					summary.setQueryCount(summary.getQueries().size());
				}
			}
		}

		return new ArrayList<>(summaryMap.values());
	}

	/**
	 * Fetch all enquiries, group by GeoLocation, and build LocationStats DTOs.
	 */
	@Override
	public List<LocationStats> getLocationStats(String storeCode, LocalDate startDate, LocalDate endDate) {
		try {
			// 1) fetch all sessions
			List<CustomerEnquiries> all = customerEnquiriesRepo.findAll();

			// 2) filter by storeCode (required) and by date range (optional)
			List<CustomerEnquiries> filtered = all.stream().filter(ce -> storeCode.equalsIgnoreCase(ce.getStoreCode()))
					.filter(ce -> {
						// if no date filters provided, keep all
						if (startDate == null && endDate == null) {
							return true;
						}
						// otherwise, keep sessions that have at least one enquiry in range
						return ce.getEnquiryList().stream().flatMap(list -> list.getEnquiries().stream()).map(item -> {
							try {
								// parse the ISO‐8601 timestamp to LocalDate
								return Instant.parse(item.getTimestamp()).atZone(ZoneOffset.UTC).toLocalDate();
							} catch (Exception e) {
								return null;
							}
						}).filter(Objects::nonNull).anyMatch(date -> (startDate == null || !date.isBefore(startDate))
								&& (endDate == null || !date.isAfter(endDate)));
					}).collect(Collectors.toList());

			// 3) group by GeoLocation
			Map<GeoLocation, StatsAccumulator> acc = new HashMap<>();
			for (CustomerEnquiries ce : filtered) {
				if (ce.getSessionMetadata() == null)
					continue;
				GeoLocation geo = ce.getSessionMetadata().getGeoLocation();
				String platform = ce.getSessionMetadata().getPlatform();
				if (geo == null || platform == null)
					continue;

				StatsAccumulator a = acc.computeIfAbsent(geo, k -> new StatsAccumulator());
				a.incrementTotal();
				if ("mobile".equalsIgnoreCase(platform)) {
					a.incrementMobile();
				} else if ("web".equalsIgnoreCase(platform)) {
					a.incrementWeb();
				}
			}

			// 4) build and return DTOs
			return acc.entrySet().stream()
					.map(e -> new LocationStats(e.getKey().getCity(), e.getKey().getCountry(), e.getKey().getLatitude(),
							e.getKey().getLongitude(), e.getValue().getTotal(),
							new LocationPlatformBreakdown(e.getValue().getMobile(), e.getValue().getWeb())))
					.collect(Collectors.toList());

		} catch (Exception ex) {
			logger.error("Error fetching location stats", ex);
			return Collections.emptyList();
		}
	}

	// helper accumulator class
	private static class StatsAccumulator {
		private long total = 0, mobile = 0, web = 0;

		void incrementTotal() {
			total++;
		}

		void incrementMobile() {
			mobile++;
		}

		void incrementWeb() {
			web++;
		}

		long getTotal() {
			return total;
		}

		long getMobile() {
			return mobile;
		}

		long getWeb() {
			return web;
		}
	}

	@Override
	public PlatformOsDistributionResponse getPlatformOsDistributionForStore(String storeCode, LocalDate startDate,
			LocalDate endDate) {
		// Fallback if dates are null
		if (startDate == null) {
			startDate = LocalDate.now().minusDays(30);
		}
		if (endDate == null) {
			endDate = LocalDate.now();
		}

		// Convert LocalDate to Instant
		Instant start = startDate.atStartOfDay().toInstant(ZoneOffset.UTC);
		Instant end = endDate.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC); // Inclusive

		// Filter enquiries for store and date range
		List<CustomerEnquiries> enquiries = customerEnquiriesRepo.findAll().stream()
				.filter(e -> storeCode.equalsIgnoreCase(e.getStoreCode()))
				.filter(e -> hasEnquiryInRange(e.getEnquiryList(), start, end)).collect(Collectors.toList());

		Map<String, PlatformBreakdown> platformMap = new HashMap<>();
		int totalSessions = 0;

		for (CustomerEnquiries enquiry : enquiries) {
			SessionMetadata metadata = enquiry.getSessionMetadata();
			if (metadata == null || metadata.getPlatform() == null)
				continue;

			String platform = metadata.getPlatform().toLowerCase();
			String os = extractOS(metadata, platform);
			if (os == null)
				continue;

			platformMap.putIfAbsent(platform, new PlatformBreakdown());
			PlatformBreakdown detail = platformMap.get(platform);

			detail.setTotal(detail.getTotal() + 1);

			if (detail.getOsBreakdown() == null) {
				detail.setOsBreakdown(new HashMap<>());
			}

			detail.getOsBreakdown().put(os, detail.getOsBreakdown().getOrDefault(os, (int) 0L) + 1);
			totalSessions++;
		}

		PlatformOsDistributionResponse response = new PlatformOsDistributionResponse();
		response.setTotalSessions(totalSessions);
		response.setPlatformBreakdown(platformMap);
		return response;
	}

	private boolean hasEnquiryInRange(List<EnquiryList> enquiryLists, Instant start, Instant end) {
		if (enquiryLists == null)
			return false;

		return enquiryLists.stream().flatMap(el -> el.getEnquiries().stream()).anyMatch(item -> {
			try {
				Instant timestamp = Instant.parse(item.getTimestamp());
				return !timestamp.isBefore(start) && timestamp.isBefore(end);
			} catch (Exception e) {
				return false;
			}
		});
	}

	private String extractOS(SessionMetadata metadata, String platform) {
		if ("mobile".equals(platform) && metadata.getMobileMetadata() != null
				&& metadata.getMobileMetadata().getDevice() != null) {
			return metadata.getMobileMetadata().getDevice().getOs();
		} else if ("web".equals(platform) && metadata.getWebMetadata() != null
				&& metadata.getWebMetadata().getUserAgent() != null) {
			return metadata.getWebMetadata().getOs();
		}
		return "Unknown";
	}

	@Override
	public PushNotificationStatsResponse getPushNotificationStats(String storeCode, LocalDate start, LocalDate end) {
		List<CustomerEnquiries> customers = customerEnquiriesRepo.findByStoreCode(storeCode);

		int mobileEnabled = 0, mobileDisabled = 0, mobileReceived = 0, totalMobileUsers = 0;
		int webEnabled = 0, webDisabled = 0, webReceived = 0, totalWebUsers = 0;

		for (CustomerEnquiries c : customers) {
			if (c.getSessionMetadata() == null)
				continue;

			// mobile
			if (c.getSessionMetadata().getMobileMetadata() != null) {
				var push = c.getSessionMetadata().getMobileMetadata().getPushNotifications();
				if (push != null) {
					if (push.isEnabled()) {
						mobileEnabled++;
					} else {
						mobileDisabled++;
					}
					totalMobileUsers++;

					String receivedStr = push.getLastNotificationReceived();
					if (receivedStr != null && !receivedStr.isBlank()) {
						try {
							Instant received = Instant.parse(receivedStr);
							LocalDate date = received.atZone(ZoneId.systemDefault()).toLocalDate();
							if ((start == null || !date.isBefore(start)) && (end == null || !date.isAfter(end))) {
								mobileReceived++;
							}
						} catch (DateTimeParseException e) {
							System.out.println("Invalid date format (mobile): " + receivedStr);
						}
					}
				}
			}

			// web
			if (c.getSessionMetadata().getWebMetadata() != null) {
				var push = c.getSessionMetadata().getWebMetadata().getPushNotifications();
				if (push != null) {
					if (push.isEnabled()) {
						webEnabled++;
					} else {
						webDisabled++;
					}
					totalWebUsers++;

					String receivedStr = push.getLastNotificationReceived();
					if (receivedStr != null && !receivedStr.isBlank()) {
						try {
							Instant received = Instant.parse(receivedStr);
							LocalDate date = received.atZone(ZoneId.systemDefault()).toLocalDate();
							if ((start == null || !date.isBefore(start)) && (end == null || !date.isAfter(end))) {
								webReceived++;
							}
						} catch (DateTimeParseException e) {
							System.out.println("Invalid date format (web): " + receivedStr);
						}
					}
				}
			}
		}

		PushNotificationStats mobileStats = new PushNotificationStats(mobileEnabled, mobileDisabled, mobileReceived);
		PushNotificationStats webStats = new PushNotificationStats(webEnabled, webDisabled, webReceived);

		return new PushNotificationStatsResponse(totalMobileUsers, mobileStats, totalWebUsers, webStats);
	}

	 @Override
	    public List<QueryAnalyticsResponse> analyzeEnquiries(String storeCode, LocalDate startDate, LocalDate endDate) {
	        List<CustomerEnquiries> enquiries;

	        if (storeCode != null) {
	            enquiries = customerEnquiriesRepo.findByStoreCode(storeCode);
	        } else {
	            // No storeCode filter, fetch all entries (you might want to optimize this!)
	        	enquiries = customerEnquiriesRepo.findAll()
	        	        .stream()
	        	        .filter(e -> e.getStoreCode() != null && e.getStoreCode().startsWith("aspiresys-ai"))
	        	        .collect(Collectors.toList());
	        }

	        // Now analyze the data using your core logic
	        return analyzeEnquiries(enquiries, storeCode, startDate, endDate);
	    }

	 @Override
    public List<QueryAnalyticsResponse> analyzeEnquiries(List<CustomerEnquiries> enquiries, String storeCode,
                                                              LocalDate startDate, LocalDate endDate) {

	     Optional<String> storeCodeOpt = Optional.ofNullable(storeCode);
	     Optional<LocalDate> startDateOpt = Optional.ofNullable(startDate);
	     Optional<LocalDate> endDateOpt = Optional.ofNullable(endDate);

	     Map<String, QueryAnalyticsResponse> functionalityMap = new HashMap<>();

	     for (CustomerEnquiries enquiry : enquiries) {
	         String actualStoreCode = enquiry.getStoreCode();

	         // Filter by store code
	         if (storeCodeOpt.isPresent()) {
	             if (!storeCodeOpt.get().equals(actualStoreCode)) continue;
	         } else {
	             if (actualStoreCode == null || !actualStoreCode.startsWith("aspiresys-ai")) continue;
	         }

	         // Get or create store-level response
	         QueryAnalyticsResponse response = functionalityMap
	                 .computeIfAbsent(actualStoreCode, k -> new QueryAnalyticsResponse(actualStoreCode));

	         if (enquiry.getEnquiryList() == null) continue;

	         // Determine user ID
	         String user = enquiry.getCustomerId() != null ? enquiry.getCustomerId() : enquiry.getSessionId();
	         if (user == null) continue;

	         for (EnquiryList group : enquiry.getEnquiryList()) {
	             if (group.getEnquiries() == null) continue;

	             for (EnquiryItem entry : group.getEnquiries()) {
	                 if (entry.getTimestamp() == null || entry.getQueryText() == null) continue;

	                 // Parse date from timestamp
	                 LocalDate date;
	                 try {
	                     date = Instant.parse(entry.getTimestamp())
	                             .atZone(ZoneId.systemDefault())
	                             .toLocalDate();
	                 } catch (DateTimeParseException e) {
	                     continue;
	                 }

	                 // Apply date filters
	                 if (startDateOpt.isPresent() && date.isBefore(startDateOpt.get())) continue;
	                 if (endDateOpt.isPresent() && date.isAfter(endDateOpt.get())) continue;

	                 // Get or create date-level detail
	                 FunctionalityDetail detail = response.getDetails().stream()
	                         .filter(d -> d.getDate().equals(date.toString()))
	                         .findFirst()
	                         .orElseGet(() -> {
	                             FunctionalityDetail newDetail = new FunctionalityDetail(date);
	                             response.getDetails().add(newDetail);
	                             return newDetail;
	                         });

	                 // Delegate logic to add question (handles user grouping and date counts)
	                 detail.addQuestion(
	                         user,
	                         entry.getQueryText(),
	                         entry.getMainResponse(),
	                         entry.getKeywords(),
	                         entry.getPotentialQuestions()
	                 );

	                 // Count at store level
	                 response.incrementQueryCount();
	             }
	         }
	     }

            return new ArrayList<>(functionalityMap.values());
        }

       @Override
       public List<Customer> getInactiveCustomers(int days) {
               try {
                       List<Customer> all = customerRepo.findAll();
                       Instant cutoff = Instant.now().minus(days, java.time.temporal.ChronoUnit.DAYS);
                       return all.stream().filter(c -> {
                               if (c.getLastLogin() == null)
                                       return true;
                               try {
                                       Instant last = Instant.parse(c.getLastLogin());
                                       return last.isBefore(cutoff);
                               } catch (Exception e) {
                                       return false;
                               }
                       }).collect(Collectors.toList());
               } catch (Exception e) {
                       logger.error("Error fetching inactive customers", e);
                       return Collections.emptyList();
               }
       }
	    
}