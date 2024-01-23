package org.nttdatabc.mscustomer.utils;

import org.nttdatabc.mscustomer.model.AuthorizedSigner;
import org.nttdatabc.mscustomer.model.Customer;
import org.nttdatabc.mscustomer.model.TypeCustomer;
import org.nttdatabc.mscustomer.repository.CustomerRepository;
import org.nttdatabc.mscustomer.utils.exceptions.errors.ErrorResponseException;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.Predicate;

import static org.nttdatabc.mscustomer.utils.Constantes.*;

public class CustomerValidator {
    public static void validateCustomerNoNulls(Customer customer) throws ErrorResponseException {
        Optional.of(customer)
                .filter(c -> c.getIdentifier() != null)
                .filter(c -> c.getFullname() != null)
                .filter(c -> c.getType() != null)
                .filter(c -> c.getAddress() != null)
                .filter(c -> c.getPhone() != null)
                .filter(c -> c.getEmail() != null)
                .filter(c -> c.getBirthday() != null)
                .orElseThrow(() -> new ErrorResponseException(EX_ERROR_REQUEST, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST));
    }
    public  static void validateCustomerEmpty(Customer customer) throws ErrorResponseException {
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
    public static void verifyTypePerson(Customer customer)throws ErrorResponseException{
        Predicate<Customer> existTypePerson = customerValidate -> customerValidate
                .getType()
                .equalsIgnoreCase(TypeCustomer.PERSONA.toString()) ||
                customerValidate.getType().equalsIgnoreCase(TypeCustomer.EMPRESA.toString());
        if(existTypePerson.negate().test(customer)){
            throw new ErrorResponseException(EX_ERROR_TYPE_PERSONA,HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateUserNotRegistred(String customerId, CustomerRepository customerRepository) throws ErrorResponseException {
        Optional<Customer> customer = customerRepository.findByIdentifier(customerId);
        if(customer.isPresent()){
            throw new ErrorResponseException(EX_USER_REGISTRED,HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateAuthorizedSignerNoNulls(AuthorizedSigner authorizedSigner) throws ErrorResponseException {
        Optional.of(authorizedSigner)
                .filter(c -> c.getCargo() != null)
                .filter(c -> c.getFullname() != null)
                .filter(c -> c.getDni() != null)
                .orElseThrow(() -> new ErrorResponseException(EX_ERROR_REQUEST,HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST));
    }
    public static  void validateAuthorizedSignerEmpty(AuthorizedSigner authorizedSigner) throws ErrorResponseException {
        Optional.of(authorizedSigner)
                .filter(c -> !c.getCargo().isBlank())
                .filter(c -> !c.getFullname().isBlank())
                .filter(c -> !c.getDni().isBlank())
                .orElseThrow(() -> new ErrorResponseException(EX_VALUE_EMPTY,HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST));
    }

    public static void validateAuthorizedSignerOnlyEmpresa(Customer customer) throws ErrorResponseException{
        if(customer.getType().equalsIgnoreCase(TypeCustomer.PERSONA.toString()) && customer.getAuthorizedSigners() != null){
            throw  new ErrorResponseException(EX_ERROR_PERSONA_AUTHORIZED_SIGNER,HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
        }
    }
}
