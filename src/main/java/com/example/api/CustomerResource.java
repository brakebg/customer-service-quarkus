package com.example.api;

import com.example.model.Customer;
import com.example.dto.CustomerRequestDTO;
import com.example.dto.CustomerResponseDTO;
import com.example.dto.LocationResponseDTO;
import com.example.model.Location;
import com.example.repository.CustomerRepository;
import com.example.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/customers")
public class CustomerResource {

    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    public CustomerResource(CustomerRepository customerRepository, CustomerService customerService) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createCustomer(CustomerRequestDTO customerRequest) {
        // Use CustomerService to create customer and get response DTO
        CustomerResponseDTO responseDTO = customerService.createCustomer(customerRequest);
        return Response.status(Response.Status.CREATED).entity(responseDTO).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCustomer(CustomerRequestDTO customerRequest) {
        // Convert DTO to entity
        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setEmail(customerRequest.getEmail());

        // Logic to update a customer
        Customer updatedCustomer = customerService.updateCustomer(customer);

        // Convert entity to DTO
        CustomerResponseDTO responseDTO = new CustomerResponseDTO();
        responseDTO.setId(updatedCustomer.getId());
        responseDTO.setName(updatedCustomer.getName());
        responseDTO.setEmail(updatedCustomer.getEmail());

        return Response.ok(responseDTO).build();
    }

    @GET
    @Path("/{customerId}/locations")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerLocations(@PathParam("customerId") Long customerId) {
        List<LocationResponseDTO> locations = customerService.getCustomerLocations(customerId);
        return Response.ok(locations).entity(locations).build();
    }

    @POST
    @Path("/{customerId}/locations")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addLocationToCustomer(@PathParam("customerId") Long customerId, Location location) {
        try {
            customerService.addLocationToCustomer(customerId, location);
            return Response.status(Response.Status.CREATED).build();
        } catch (IllegalStateException | EntityNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
