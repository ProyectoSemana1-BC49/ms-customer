package org.nttdatabc.mscustomer.controller;

import static org.nttdatabc.mscustomer.utils.Constantes.PREFIX_PATH;

import java.util.List;
import org.nttdatabc.mscustomer.api.CustomerApi;
import org.nttdatabc.mscustomer.model.AuthorizedSigner;
import org.nttdatabc.mscustomer.model.Customer;
import org.nttdatabc.mscustomer.service.CustomerServiceImpl;
import org.nttdatabc.mscustomer.utils.exceptions.errors.ErrorResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller del Customer.
 */
@RestController
@RequestMapping(PREFIX_PATH)
public class CustomerController implements CustomerApi {

  @Autowired
  private CustomerServiceImpl customerServiceImpl;

  @Override
  public ResponseEntity<Void> createCustomer(Customer customer) {
    try {
      customerServiceImpl.createCustomerService(customer);
    } catch (ErrorResponseException e) {
      throw new RuntimeException(e);
    }
    return new ResponseEntity<Void>(HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<List<Customer>> getAllCustomers() {
    List<Customer> listaCustomers = customerServiceImpl.getAllCustomersService();
    return new ResponseEntity<>(listaCustomers, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Customer> getCustomerById(String customerId) {
    Customer customerById = null;
    try {
      customerById = customerServiceImpl.getCustomerByIdService(customerId);
    } catch (ErrorResponseException e) {
      throw new RuntimeException(e);
    }
    return new ResponseEntity<>(customerById, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Void> updateCustomer(Customer customer) {
    try {
      customerServiceImpl.updateCustomerService(customer);
    } catch (ErrorResponseException e) {
      throw new RuntimeException(e);
    }
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Void> deleteCustomerById(String customerId) {
    try {
      customerServiceImpl.deleteCustomerByIdService(customerId);
    } catch (ErrorResponseException e) {
      throw new RuntimeException(e);
    }
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<List<AuthorizedSigner>> getAuthorizedSignersByCustomerId(String customerId) {
    List<AuthorizedSigner> listaAuthorized = null;
    try {
      listaAuthorized = customerServiceImpl.getAuthorizedSignersByCustomerIdService(customerId);
    } catch (ErrorResponseException e) {
      throw new RuntimeException(e);
    }
    return new ResponseEntity<>(listaAuthorized, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Void> createAuthorizedSignersByCustomerId(String customerId, AuthorizedSigner auth) {
    try {
      customerServiceImpl.createAuthorizedSignersByCustomerId(customerId, auth);
    } catch (ErrorResponseException e) {
      throw new RuntimeException(e);
    }
    return new ResponseEntity<Void>(HttpStatus.CREATED);
  }
}
