package com.example.repository;

import com.example.model.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;


@ApplicationScoped
@Slf4j
public class CustomerRepository implements PanacheRepository<Customer> {

    @PersistenceContext
    private EntityManager entityManager;

    public Customer findCustomerWithLocations(Long customerId) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("Customer.locations");
        log.info("Using entity graph: {}", entityGraph.getName());

        TypedQuery<Customer> query = entityManager.createQuery(
                "SELECT c FROM Customer c WHERE c.id = :customerId", Customer.class);
        query.setParameter("customerId", customerId);
        query.setHint("jakarta.persistence.fetchgraph", entityGraph);
        log.info("Query executed successfully for customer ID: {}", customerId);

        return query.getSingleResult();
    }
}
