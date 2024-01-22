package org.nttdatabc.mscustomer.repository;

import org.nttdatabc.mscustomer.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer>findByIdentifier(String identifier);


}
