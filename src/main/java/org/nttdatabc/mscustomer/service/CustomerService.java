package org.nttdatabc.mscustomer.service;

import java.util.List;
import org.nttdatabc.mscustomer.model.AuthorizedSigner;
import org.nttdatabc.mscustomer.model.Customer;
import org.nttdatabc.mscustomer.utils.exceptions.errors.ErrorResponseException;


/**
 * Interface de CustomerService.
 */
public interface CustomerService {
  List<Customer> getAllCustomersService();

  void createCustomerService(Customer customer) throws ErrorResponseException;

  Customer getCustomerByIdService(String customerId) throws ErrorResponseException;

  void updateCustomerService(Customer customer) throws ErrorResponseException;

  void deleteCustomerByIdService(String customerId) throws ErrorResponseException;

  List<AuthorizedSigner> getAuthorizedSignersByCustomerIdService(String customerId) throws ErrorResponseException;

  void createAuthorizedSignersByCustomerId(String customerId, AuthorizedSigner authorizedSigner) throws ErrorResponseException;
}
