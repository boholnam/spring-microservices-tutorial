package com.example.driverservice.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

@RestController
@RequestMapping("/drivers")
public class DriverServiceRestController {

	public DriverServiceRestController() {
		System.out.println("DriverServiceRestController constructor is running");
	}

	// Simulated Driver Database
	private static final Map<String, String> drivers = new HashMap<>();

	static {

		System.out.println("static block is running");

		drivers.put("1", "Driver A - Available");
		drivers.put("2", "Driver B - On Trip");
		drivers.put("3", "Driver C - On Trip");

		drivers.forEach((key, value) -> System.out.println("Driver ID: " + key + ", Status: " + value));
	}



	// Hello World Endpoint
	@GetMapping("/helloWorld")
	public String helloWorld() {
		return "Hello world from Driver Service";
	}

	// Get Driver Status
	@GetMapping("/{id}")
	public ResponseEntity<String> getDriverStatus(@PathVariable String id) {
		return ResponseEntity.ok(drivers.getOrDefault(id, "Driver not found"));
	}

	// Update Driver Status
	@PutMapping("/{id}/status")
	public ResponseEntity<String> updateDriverStatus(@PathVariable String id, @RequestParam String status) {
		drivers.put(id, status);
		return ResponseEntity.ok("Driver " + id + " status updated to " + status);
	}
}
