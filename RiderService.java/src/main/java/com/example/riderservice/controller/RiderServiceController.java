package com.example.riderservice.controller;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/riders")
public class RiderServiceController {

	private final DiscoveryClient discoveryClient;
	private final RestTemplate restTemplate;

	public RiderServiceController(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
		this.discoveryClient = discoveryClient;
		this.restTemplate = restTemplate;
	}

	@GetMapping("/request-driver/{driverId}")
	public String requestDriver(@PathVariable String driverId) {
		// Discover Driver Service instance
		List<ServiceInstance> instances = discoveryClient.getInstances("driver-service");
		if (instances.isEmpty()) {
			return "Driver Service is unavailable";
		}

		// Call Driver Service API
		String driverStatus = restTemplate.getForObject(
				instances.get(0).getUri() + "/drivers/" + driverId,
				String.class);

		return "Rider requested Driver " + driverId + ": " + driverStatus;
	}
}