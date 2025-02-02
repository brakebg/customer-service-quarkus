package com.example.service;

import com.example.dto.CustomerRequestDTO;
import com.example.dto.CustomerResponseDTO;
import com.example.dto.LocationResponseDTO;
import com.example.mapper.CustomerMapper;
import com.example.mapper.LocationMapper;
import com.example.model.Customer;
import com.example.model.Location;
import com.example.repository.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for customer operations.
 */
@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;
    private final LocationMapper locationMapper = LocationMapper.INSTANCE;

    /**
     * Creates a new customer.
     *
     * @param customerRequestDTO the customer request DTO
     * @return the created customer response DTO
     */
    @Transactional
    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO) {
        // Validate location count
        if (customerRequestDTO.getLocations() != null && customerRequestDTO.getLocations().size() > 10) {
            throw new IllegalStateException("Cannot create a customer with more than 10 locations.");
        }
        Customer customer = customerMapper.toEntity(customerRequestDTO);
        customerRepository.persist(customer);
        return customerMapper.toResponseDTO(customer);
    }

    /**
     * Updates an existing customer.
     *
     * @param customer the customer to update
     * @return the updated customer
     */
    @Transactional
    public Customer updateCustomer(Customer customer) {
        // Validate location count
        if (customer.getLocations() != null && customer.getLocations().size() > 10) {
            throw new IllegalStateException("Cannot update a customer to have more than 10 locations.");
        }
        Customer existingCustomer = customerRepository.findById(customer.getId());
        if (existingCustomer != null) {
            existingCustomer.setName(customer.getName());
            existingCustomer.setEmail(customer.getEmail());
            // Update other fields as necessary
        }
        return existingCustomer;
    }

    /**
     * Fetches all locations for a given customer.
     *
     * @param customerId the ID of the customer
     * @return the list of locations for the customer
     */
    @Transactional
    public List<LocationResponseDTO> getCustomerLocations(Long customerId) {
        Customer customer = customerRepository.findCustomerWithLocations(customerId);
        if (customer != null) {
            return customer.getLocations().stream()
                    .map(locationMapper::toResponseDTO)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * Adds a location to a customer.
     *
     * @param customerId the ID of the customer
     * @param location   the location to add
     */
    @Transactional
    public void addLocationToCustomer(Long customerId, Location location) {
        Customer customer = customerRepository.findById(customerId);
        if (customer != null) {
            if (customer.getLocations().size() >= 10) {
                throw new IllegalStateException("Cannot add more than 10 locations to a customer.");
            }
            customer.addLocation(location);
            customerRepository.persist(customer);
        } else {
            throw new EntityNotFoundException("Customer not found.");
        }
    }

    // Add more methods as needed
}
