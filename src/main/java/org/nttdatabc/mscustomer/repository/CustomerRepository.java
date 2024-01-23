package org.nttdatabc.mscustomer.repository;

import java.util.Optional;
import org.nttdatabc.mscustomer.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface del Customer Repository.
 */
@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
  Optional<Customer> findByIdentifier(String identifier);


}
