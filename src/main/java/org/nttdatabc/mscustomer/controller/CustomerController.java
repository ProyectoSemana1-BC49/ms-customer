package org.nttdatabc.mscustomer.controller;

import org.nttdatabc.mscustomer.api.CustomerApi;
import org.nttdatabc.mscustomer.model.Customer;
import org.nttdatabc.mscustomer.service.CustomerService;
import org.nttdatabc.mscustomer.utils.exceptions.errors.ErrorResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.nttdatabc.mscustomer.utils.Constantes.PREFIX_PATH;

@RestController
@RequestMapping(PREFIX_PATH)
public class CustomerController implements CustomerApi {

    @Autowired
    private CustomerService customerService;

    @Override
    public ResponseEntity<Void> createCustomer(Customer customer) throws ErrorResponseException {
        customerService.createCustomerService(customer);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer>listaCustomers = customerService.getAllCustomersService();
        return new ResponseEntity<>(listaCustomers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Customer> getCustomerById(String customerId) throws ErrorResponseException {
        Customer customerById = customerService.getCustomerByIdService(customerId);
        return new ResponseEntity<>(customerById, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateCustomer(Customer customer) throws ErrorResponseException {
        customerService.updateCustomerService(customer);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }


}
