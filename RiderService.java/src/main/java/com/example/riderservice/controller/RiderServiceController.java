package com.example.riderservice.controller;

// Import required Spring Cloud and web dependencies
import org.springframework.cloud.client.ServiceInstance; // Represents a service instance registered with a discovery client
import org.springframework.cloud.client.discovery.DiscoveryClient; // Interface for service discovery
import org.springframework.web.bind.annotation.GetMapping; // Annotation for mapping HTTP GET requests
import org.springframework.web.bind.annotation.PathVariable; // Annotation to extract path variables from URLs
import org.springframework.web.bind.annotation.RequestMapping; // Base URL mapping for the controller
import org.springframework.web.bind.annotation.RestController; // Marks the class as a REST controller
import org.springframework.web.client.RestTemplate; // For performing HTTP requests

import java.util.List; // Import List for handling multiple service instances

/**
 * RiderServiceController is responsible for handling rider-related API requests.
 * It uses DiscoveryClient to discover driver-service instances dynamically
 * and RestTemplate to communicate with those instances.
 */
@RestController
@RequestMapping("/riders") // Base URL for all endpoints in this controller
public class RiderServiceController {

	// Dependency Injection for DiscoveryClient to perform service discovery
	private final DiscoveryClient discoveryClient;

	// Dependency Injection for RestTemplate to perform HTTP calls
	private final RestTemplate restTemplate;

	/**
	 * Constructor for RiderServiceController.
	 * @param discoveryClient Used for discovering driver-service instances.
	 * @param restTemplate Used for making HTTP requests to discovered services.
	 */
	public RiderServiceController(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
		this.discoveryClient = discoveryClient;
		this.restTemplate = restTemplate;
	}

	/**
	 * Handles GET requests for requesting a driver's status.
	 * @param driverId The ID of the driver to request status for.
	 * @return A string containing the driver's status.
	 */
	@GetMapping("/request-driver/{driverId}") // Endpoint to request driver status
	public String requestDriver(@PathVariable String driverId) {
		// Discover instances of the Driver Service using the service name
		List<ServiceInstance> instances = discoveryClient.getInstances("driver-service");

		// Check if any driver-service instances are available
		if (instances.isEmpty()) {
			return "Driver service not found";
		}

		// Retrieve the first available service instance
		ServiceInstance instance = instances.get(0);
		if (instance == null) {
			return "No valid driver service instance found";
		}

		// Build the URL dynamically using the service instance URI
		String driverStatus = restTemplate.getForObject(
				instance.getUri() + "/drivers/" + driverId, // Constructs the full driver-service URL
				String.class // Response type
		);

		// Return the retrieved driver status
		return "Rider requested driver " + driverId + " status: " + driverStatus;
	}
}