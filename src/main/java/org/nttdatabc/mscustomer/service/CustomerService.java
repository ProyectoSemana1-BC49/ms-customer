package org.nttdatabc.mscustomer.service;

import org.checkerframework.checker.units.qual.C;
import org.nttdatabc.mscustomer.model.Customer;
import org.nttdatabc.mscustomer.model.TypeCustomer;
import org.nttdatabc.mscustomer.repository.CustomerRepository;
import org.nttdatabc.mscustomer.utils.Utilitarios;
import org.nttdatabc.mscustomer.utils.exceptions.errors.ErrorResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.nttdatabc.mscustomer.utils.Constantes.*;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomersService(){
        return customerRepository.findAll();
    }
    public void createCustomerService(Customer customer) throws ErrorResponseException {
        validateCustomerNoNulls(customer);
        validateCustomerEmpty(customer);
        verifyTypePerson(customer);
        validateUserNotRegistred(customer.getIdentifier());

        customer.setId(Utilitarios.generateUUID());
        customerRepository.save(customer);
    }
    public Customer getCustomerByIdService(String customerId) throws ErrorResponseException {
        Optional<Customer> customer = customerRepository.findByIdentifier(customerId);
        return customer.orElseThrow(() -> new ErrorResponseException(EX_NOT_FOUND_RECURSO, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND));
    }

    public void updateCustomerService(Customer customer) throws ErrorResponseException {
        validateCustomerNoNulls(customer);
        validateCustomerEmpty(customer);
        verifyTypePerson(customer);
        Optional<Customer> custFindByIdOptional = customerRepository.findById(customer.getId());
        if(custFindByIdOptional.isEmpty()){
            throw new ErrorResponseException(EX_NOT_FOUND_RECURSO,HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
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

    /*===================================================================================*/
    public  void validateCustomerNoNulls(Customer customer) throws ErrorResponseException {
        Optional.of(customer)
                .filter(c -> c.getIdentifier() != null)
                .filter(c -> c.getFullname() != null)
                .filter(c -> c.getType() != null)
                .filter(c -> c.getAddress() != null)
                .filter(c -> c.getPhone() != null)
                .filter(c -> c.getEmail() != null)
                .filter(c -> c.getBirthday() != null)
                .orElseThrow(() -> new ErrorResponseException(EX_ERROR_REQUEST,HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST));
    }
    public  void validateCustomerEmpty(Customer customer) throws ErrorResponseException {
        Optional.of(customer)
                .filter(c -> !c.getIdentifier().isBlank())
                .filter(c -> !c.getFullname().isBlank())
                .filter(c -> !c.getType().isBlank())
                .filter(c -> !c.getAddress().isBlank())
                .filter(c -> !c.getPhone().isBlank())
                .filter(c -> !c.getEmail().isBlank())
                .filter(c -> !c.getBirthday().isBlank())
                .orElseThrow(() -> new ErrorResponseException(EX_VALUE_EMPTY,HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST));
    }
    public  void verifyTypePerson(Customer customer)throws ErrorResponseException{
        Predicate<Customer>existTypePerson = customerValidate -> customerValidate
                .getType()
                .equalsIgnoreCase(TypeCustomer.valueOf("PERSONA").toString()) ||
                customerValidate.getType().equalsIgnoreCase(TypeCustomer.valueOf("EMPRESA").toString());
        if(existTypePerson.negate().test(customer)){
            throw new ErrorResponseException(EX_ERROR_REQUEST,HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
        }
    }
    public void validateUserNotRegistred(String customerId) throws ErrorResponseException {
        Optional<Customer> customer = customerRepository.findByIdentifier(customerId);
        if(customer.isPresent()){
            throw new ErrorResponseException(EX_USER_REGISTRED,HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
        }
    }

}