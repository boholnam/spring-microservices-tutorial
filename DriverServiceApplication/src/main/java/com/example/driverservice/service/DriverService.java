package com.example.driverservice.service;

// Import necessary classes for the service
import com.example.driverservice.model.Driver; // Import the Driver model
import com.example.driverservice.repository.DriverRepository; // Import the repository for database operations
import org.springframework.beans.factory.annotation.Autowired; // Annotation for dependency injection
import org.springframework.stereotype.Service; // Marks this class as a Spring service component
import com.example.driverservice.exception.DriverNotFoundException;

import java.util.Optional; // Utility class to handle nullable return values

/**
 * Service layer responsible for handling business logic related to Driver operations.
 */
@Service // Marks this class as a Spring-managed service bean
public class DriverService {

    // Dependency on the DriverRepository to interact with the database
    private final DriverRepository driverRepository;

    /**
     * Constructor-based dependency injection for DriverRepository.
     * @param driverRepository The repository used for accessing Driver data.
     */
    @Autowired // Spring injects an instance of DriverRepository automatically
    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository; // Assign the injected repository to the class field
    }

    /**
     * Find a Driver by their ID.
     * @param id The ID of the driver to find.
     * @return An Optional containing the Driver if found, or empty if not.
     */
    public Optional<Driver> findById(String id) {
        return driverRepository.findById(id); // Fetch driver by ID from the database
    }

    /**
     * Retrieve the status of a driver by their ID.
     * @param id The ID of the driver.
     * @return The status of the driver if found, or a "Driver not found" message.
     */
    public String getDriverStatus(String id) {
        // Find driver by ID, map to their status, or return a default "Driver not found" message
        return driverRepository.findById(id)
                .map(Driver::getStatus) // Extract status if the driver exists
                .orElseThrow(() -> new DriverNotFoundException("Driver with ID " + id + " not found"));
    }

    /**
     * Update the status of a driver by their ID.
     * @param id The ID of the driver.
     * @param status The new status to set for the driver.
     */
    public void updateDriverStatus(String id, String status) {
        // Find driver by ID and update their status if present
        driverRepository.findById(id).ifPresent(driver -> {
            driver.setStatus(status); // Set the new status for the driver
            driverRepository.save(driver); // Save the updated driver object to the database
        });
    }

    /**
     * Add a new driver to the system.
     * @param driver The Driver object to be added.
     */
    public void addDriver(Driver driver) {
        driverRepository.save(driver); // Save the new driver object to the database
    }
}