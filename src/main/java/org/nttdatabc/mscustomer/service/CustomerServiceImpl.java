package org.nttdatabc.mscustomer.service;


import static org.nttdatabc.mscustomer.utils.Constantes.EX_NOT_FOUND_RECURSO;
import static org.nttdatabc.mscustomer.utils.CustomerValidator.validateAuthorizedSignerEmpty;
import static org.nttdatabc.mscustomer.utils.CustomerValidator.validateAuthorizedSignerNoNulls;
import static org.nttdatabc.mscustomer.utils.CustomerValidator.validateAuthorizedSignerOnlyEmpresa;
import static org.nttdatabc.mscustomer.utils.CustomerValidator.validateCustomerEmpty;
import static org.nttdatabc.mscustomer.utils.CustomerValidator.validateCustomerNoNulls;
import static org.nttdatabc.mscustomer.utils.CustomerValidator.validateUserNotRegistred;
import static org.nttdatabc.mscustomer.utils.CustomerValidator.verifyTypePerson;
import static org.nttdatabc.mscustomer.utils.Utilitarios.generateUuid;

import java.util.List;
import java.util.Optional;
import org.nttdatabc.mscustomer.model.AuthorizedSigner;
import org.nttdatabc.mscustomer.model.Customer;
import org.nttdatabc.mscustomer.repository.CustomerRepository;
import org.nttdatabc.mscustomer.utils.exceptions.errors.ErrorResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


/**
 * Clase del CustomerServiceImpl.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
  @Autowired
  private CustomerRepository customerRepository;

  @Override
  public List<Customer> getAllCustomersService() {
    return customerRepository.findAll();
  }

  @Override
  public void createCustomerService(Customer customer) throws ErrorResponseException {
    validateCustomerNoNulls(customer);
    validateCustomerEmpty(customer);
    verifyTypePerson(customer);
    validateUserNotRegistred(customer.getIdentifier(), customerRepository);
    validateAuthorizedSignerOnlyEmpresa(customer);

    customer.setId(generateUuid());
    customerRepository.save(customer);
  }

  @Override
  public Customer getCustomerByIdService(String customerId) throws ErrorResponseException {
    Optional<Customer> customer = customerRepository.findById(customerId);
    return customer.orElseThrow(() ->
        new ErrorResponseException(EX_NOT_FOUND_RECURSO,
            HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
  }

  @Override
  public void updateCustomerService(Customer customer) throws ErrorResponseException {
    validateCustomerNoNulls(customer);
    validateCustomerEmpty(customer);
    verifyTypePerson(customer);
    Optional<Customer> custFindByIdOptional = customerRepository.findById(customer.getId());
    if (custFindByIdOptional.isEmpty()) {
      throw new ErrorResponseException(EX_NOT_FOUND_RECURSO,
          HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
    }
    Customer customerFound = custFindByIdOptional.get();
    customerFound.setAddress(customer.getAddress());
    customerFound.setBirthday(customer.getBirthday());
    customerFound.setEmail(customer.getEmail());
    customerFound.setFullname(customer.getFullname());
    customerFound.setPhone(customer.getPhone());
    customerFound.setAuthorizedSigners(customer.getAuthorizedSigners());
    customerRepository.save(customerFound);
  }

  @Override
  public void deleteCustomerByIdService(String customerId) throws ErrorResponseException {
    Optional<Customer> custFindByIdOptional = customerRepository.findById(customerId);
    if (custFindByIdOptional.isEmpty()) {
      throw new ErrorResponseException(EX_NOT_FOUND_RECURSO,
          HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
    }
    customerRepository.delete(custFindByIdOptional.get());
  }

  @Override
  public List<AuthorizedSigner> getAuthorizedSignersByCustomerIdService(String customerId) throws ErrorResponseException {

    Optional<Customer> customer = customerRepository.findById(customerId);
    if (customer.isEmpty()) {
      throw new ErrorResponseException(EX_NOT_FOUND_RECURSO,
          HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
    }
    return customer.get().getAuthorizedSigners();
  }

  @Override
  public void createAuthorizedSignersByCustomerId(String customerId, AuthorizedSigner authorizedSigner) throws ErrorResponseException {
    validateAuthorizedSignerNoNulls(authorizedSigner);
    validateAuthorizedSignerEmpty(authorizedSigner);
    Optional<Customer> customer = customerRepository.findById(customerId);
    if (customer.isEmpty()) {
      throw new ErrorResponseException(EX_NOT_FOUND_RECURSO,
          HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
    }
    List<AuthorizedSigner> existingSigners = customer.get().getAuthorizedSigners();
    existingSigners.add(authorizedSigner);
    customerRepository.save(customer.get());
  }


}
