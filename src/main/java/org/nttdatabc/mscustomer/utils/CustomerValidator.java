package org.nttdatabc.mscustomer.utils;

import static org.nttdatabc.mscustomer.utils.Constantes.EX_ERROR_PERSON_AUTH_SIGNER;
import static org.nttdatabc.mscustomer.utils.Constantes.EX_ERROR_REQUEST;
import static org.nttdatabc.mscustomer.utils.Constantes.EX_ERROR_TYPE_PERSONA;
import static org.nttdatabc.mscustomer.utils.Constantes.EX_USER_REGISTRED;
import static org.nttdatabc.mscustomer.utils.Constantes.EX_VALUE_EMPTY;

import java.util.Optional;
import java.util.function.Predicate;
import org.nttdatabc.mscustomer.model.AuthorizedSigner;
import org.nttdatabc.mscustomer.model.Customer;
import org.nttdatabc.mscustomer.model.TypeCustomer;
import org.nttdatabc.mscustomer.repository.CustomerRepository;
import org.nttdatabc.mscustomer.utils.exceptions.errors.ErrorResponseException;
import org.springframework.http.HttpStatus;

/**
 * Clase para las validaciones del input del Customer.
 */
public class CustomerValidator {
  /**
   * Valida que los valores no vengan nulos.
   *
   * @param customer el input.
   * @throws ErrorResponseException excepcion que se dispara.
   */
  public static void validateCustomerNoNulls(Customer customer) throws ErrorResponseException {
    Optional.of(customer)
        .filter(c -> c.getIdentifier() != null)
        .filter(c -> c.getFullname() != null)
        .filter(c -> c.getType() != null)
        .filter(c -> c.getAddress() != null)
        .filter(c -> c.getPhone() != null)
        .filter(c -> c.getEmail() != null)
        .filter(c -> c.getBirthday() != null)
        .orElseThrow(() -> new ErrorResponseException(EX_ERROR_REQUEST,
            HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST));
  }

  /**
   * Valida que los valores no vengan vacíos.
   *
   * @param customer el input.
   * @throws ErrorResponseException excepcion que se dispara.
   */
  public static void validateCustomerEmpty(Customer customer) throws ErrorResponseException {
    Optional.of(customer)
        .filter(c -> !c.getIdentifier().isBlank())
        .filter(c -> !c.getFullname().isBlank())
        .filter(c -> !c.getType().isBlank())
        .filter(c -> !c.getAddress().isBlank())
        .filter(c -> !c.getPhone().isBlank())
        .filter(c -> !c.getEmail().isBlank())
        .filter(c -> !c.getBirthday().isBlank())
        .orElseThrow(() -> new ErrorResponseException(EX_VALUE_EMPTY,
            HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST));
  }

  /**
   * Verifica que el tipo de persona sea PERSONA | EMPRESA.
   *
   * @param customer input.
   * @throws ErrorResponseException error que se dispara.
   */
  public static void verifyTypePerson(Customer customer) throws ErrorResponseException {
    Predicate<Customer> existTypePerson = customerValidate -> customerValidate
        .getType()
        .equalsIgnoreCase(TypeCustomer.PERSONA.toString())
        || customerValidate.getType().equalsIgnoreCase(TypeCustomer.EMPRESA.toString());
    if (existTypePerson.negate().test(customer)) {
      throw new ErrorResponseException(EX_ERROR_TYPE_PERSONA,
          HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Valida que el usuario no este registrado, para poder registrarlo.
   *
   * @param customerId  id del customer
   * @param customerRepository clase del repositorio
   * @throws ErrorResponseException error que se dispara.
   */
  public static void validateUserNotRegistred(String customerId, CustomerRepository customerRepository) throws ErrorResponseException {
    Optional<Customer> customer = customerRepository.findByIdentifier(customerId);
    if (customer.isPresent()) {
      throw new ErrorResponseException(EX_USER_REGISTRED, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Valida inputs del authorized.
   *
   * @param authorizedSigner input.
   * @throws ErrorResponseException error que se dispara.
   */
  public static void validateAuthorizedSignerNoNulls(AuthorizedSigner authorizedSigner) throws ErrorResponseException {
    Optional.of(authorizedSigner)
        .filter(c -> c.getCargo() != null)
        .filter(c -> c.getFullname() != null)
        .filter(c -> c.getDni() != null)
        .orElseThrow(() -> new ErrorResponseException(EX_ERROR_REQUEST,
            HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST));
  }

  /**
   *Validar que el authorized no venga vacio.
   *
   * @param authorizedSigner input.
   * @throws ErrorResponseException error que dispara.
   */
  public static void validateAuthorizedSignerEmpty(AuthorizedSigner authorizedSigner) throws ErrorResponseException {
    Optional.of(authorizedSigner)
        .filter(c -> !c.getCargo().isBlank())
        .filter(c -> !c.getFullname().isBlank())
        .filter(c -> !c.getDni().isBlank())
        .orElseThrow(() -> new ErrorResponseException(EX_VALUE_EMPTY,
            HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST));
  }

  /**
   * Validar que persona no tenga authorized.
   *
   * @param customer input.
   * @throws ErrorResponseException error que dispara.
   */
  public static void validateAuthorizedSignerOnlyEmpresa(Customer customer) throws ErrorResponseException {
    if (customer.getType().equalsIgnoreCase(TypeCustomer.PERSONA.toString())
        && customer.getAuthorizedSigners() != null) {
      throw new ErrorResponseException(EX_ERROR_PERSON_AUTH_SIGNER,
          HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
    }
  }
}
