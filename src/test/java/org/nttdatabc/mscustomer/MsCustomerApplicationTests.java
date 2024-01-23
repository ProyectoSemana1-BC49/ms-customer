package org.nttdatabc.mscustomer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.nttdatabc.mscustomer.model.AuthorizedSigner;
import org.nttdatabc.mscustomer.model.Customer;
import org.nttdatabc.mscustomer.repository.CustomerRepository;
import org.nttdatabc.mscustomer.service.CustomerServiceImpl;
import org.nttdatabc.mscustomer.utils.exceptions.errors.ErrorResponseException;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MsCustomerApplicationTests {

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private CustomerServiceImpl customerService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	@Test
	void getAllCustomersService() {
		// Arrange
		when(customerRepository.findAll()).thenReturn(Collections.singletonList(new Customer()));

		// Act
		List<Customer> result = customerService.getAllCustomersService();

		// Assert
		assertNotNull(result);
		assertFalse(result.isEmpty());
	}
	@Test
	void createCustomerService() throws ErrorResponseException {
		// Arrange
		Customer customer = new Customer();
		customer.setIdentifier("testIdentifier");
		customer.setPhone("34234324");
		customer.setFullname("fullname");
		customer.setAddress("direccion");
		customer.setBirthday("2020-01-01");
		customer.setType("persona");
		customer.setEmail("vtrtr@dfsd.com");

		when(customerRepository.save(any())).thenReturn(customer);

		// Act
		assertDoesNotThrow(() -> customerService.createCustomerService(customer));

		// Assert
		verify(customerRepository, times(1)).save(any());
	}

	@Test
	void getCustomerByIdService() throws ErrorResponseException {
		// Arrange
		String customerId = "testCustomerId";
		Customer expectedCustomer = new Customer();
		expectedCustomer.setId(customerId);

		when(customerRepository.findById(customerId)).thenReturn(Optional.of(expectedCustomer));

		// Act
		Customer result = assertDoesNotThrow(() -> customerService.getCustomerByIdService(customerId));

		// Assert
		assertNotNull(result);
		assertEquals(expectedCustomer, result);
		verify(customerRepository, times(1)).findById(customerId);
	}
	@Test
	void updateCustomerService() throws ErrorResponseException {
		// Arrange
		String customerId = "testCustomerId";
		Customer customerToUpdate = new Customer();
		customerToUpdate.setId(customerId);
		customerToUpdate.setId(customerId);
		customerToUpdate.setIdentifier("testIdentifier");
		customerToUpdate.setPhone("34234324");
		customerToUpdate.setFullname("fullname");
		customerToUpdate.setAddress("direccion");
		customerToUpdate.setBirthday("2020-01-01");
		customerToUpdate.setType("persona");
		customerToUpdate.setEmail("vtrtr@dfsd.com");
		Customer existingCustomer = new Customer();
		existingCustomer.setId(customerId);
		existingCustomer.setIdentifier("testIdentifier");
		existingCustomer.setPhone("34234324");
		existingCustomer.setFullname("fullname");
		existingCustomer.setAddress("direccion");
		existingCustomer.setBirthday("2020-01-01");
		existingCustomer.setType("persona");
		existingCustomer.setEmail("vtrtr@dfsd.com");

		when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
		when(customerRepository.save(any())).thenReturn(existingCustomer);

		// Act
		assertDoesNotThrow(() -> customerService.updateCustomerService(customerToUpdate));

		// Assert
		verify(customerRepository, times(1)).findById(customerId);
		verify(customerRepository, times(1)).save(existingCustomer);
	}

	@Test
	void deleteCustomerByIdService() throws ErrorResponseException {
		// Arrange
		String customerId = "testCustomerId";
		Customer existingCustomer = new Customer();
		existingCustomer.setId(customerId);

		when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));

		// Act
		assertDoesNotThrow(() -> customerService.deleteCustomerByIdService(customerId));

		// Assert
		verify(customerRepository, times(1)).findById(customerId);
		verify(customerRepository, times(1)).delete(existingCustomer);
	}

	@Test
	void getAuthorizedSignersByCustomerIdService() throws ErrorResponseException {
		// Arrange
		String customerId = "testCustomerId";
		Customer existingCustomer = new Customer();
		existingCustomer.setId(customerId);

		when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));

		// Act and Assert
		try {
			List<AuthorizedSigner> result = customerService.getAuthorizedSignersByCustomerIdService(customerId);
			assertDoesNotThrow(() -> result);
			assertEquals(existingCustomer.getAuthorizedSigners(), result);
			verify(customerRepository, times(1)).findById(customerId);
		} catch (ErrorResponseException e) {
			fail("Unexpected ErrorResponseException: " + e.getMessage());
		}
	}
	@Test
	void createAuthorizedSignersByCustomerId() throws ErrorResponseException {
		// Arrange
		String customerId = "testCustomerId";
		AuthorizedSigner authorizedSigner = new AuthorizedSigner();
		authorizedSigner.setCargo("fdsfds");
		authorizedSigner.setDni("fdsfds");
		authorizedSigner.setFullname("fdsfds");
		Customer existingCustomer = new Customer();
		existingCustomer.setId(customerId);

		when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
		when(customerRepository.save(any())).thenReturn(existingCustomer);

		// Act
		assertDoesNotThrow(() -> customerService.createAuthorizedSignersByCustomerId(customerId, authorizedSigner));

		// Assert
		verify(customerRepository, times(1)).findById(customerId);
		verify(customerRepository, times(1)).save(existingCustomer);
	}

}
