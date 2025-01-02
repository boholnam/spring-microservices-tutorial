package com.example.driverservice.controller;

import com.example.driverservice.model.Driver;
import com.example.driverservice.service.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers")
public class DriverController {
	private static final Logger logger = LoggerFactory.getLogger(DriverController.class);

	private final DriverService driverService;

	@Autowired
	public DriverController(DriverService driverService) {
		this.driverService = driverService;
	}

	@GetMapping("/helloWorld")
	public String helloWorld() {
		logger.info("Accessed helloWorld endpoint");
		return "Hello world from Driver Service";
	}

	@GetMapping("/{id}")
	public ResponseEntity<String> getDriverStatus(@PathVariable String id) {
		logger.info("Received request to get status for Driver ID: {}", id);
		String status = driverService.getDriverStatus(id);
		logger.debug("Driver status retrieved: {}", status);
		return ResponseEntity.ok(status);
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<String> updateDriverStatus(@PathVariable String id, @RequestParam String status) {
		logger.info("Received request to update status for Driver ID: {}, Status: {}", id, status);
		driverService.updateDriverStatus(id, status);
		logger.debug("Driver status updated to: {}", status);
		return ResponseEntity.ok("Driver status updated successfully!");
	}

	@PostMapping("/add")
	public ResponseEntity<String> addDriver(@RequestBody Driver driver) {
		logger.info("Received request to add new Driver with ID: {}", driver.getId());
		driverService.addDriver(driver);
		logger.debug("Driver added successfully with ID: {}", driver.getId());
		return ResponseEntity.ok("Driver added successfully!");
	}
}