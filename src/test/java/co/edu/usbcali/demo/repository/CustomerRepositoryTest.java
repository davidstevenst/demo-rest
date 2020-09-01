package co.edu.usbcali.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import co.edu.usbcali.demo.domain.Customer;



@Rollback(false)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class CustomerRepositoryTest {

	private final static Logger log=LoggerFactory.getLogger(CustomerRepositoryTest.class);
	
	private final static String email="davidstevensimbaqueva@outlook.com";
	
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Test
	@Transactional
	@Order(1)
	void save() {
	
		
		Optional<Customer> customerOptional = customerRepository.findById(email);
		
		//Siga si es falso quiere decir que ya existe
		assertFalse(customerOptional.isPresent(),"El customer ya existe");
		
		
		
		Customer customer= new Customer();
		customer.setAddress("Cra 36 c # 5 b1-29");
		customer.setEmail(email);
		customer.setEnable("Y");
		customer.setName("David Steven");
		customer.setPhone("323 288 9349");
		customer.setToken("646SDFG6SD8FGSD");
		
		customerRepository.save(customer);
		
	}
	
	@Test
	@Transactional
	@Order(2)
	void findById() {
		
		Optional<Customer> customerOptional = customerRepository.findById(email);
		
		assertTrue(customerOptional.isPresent(),"El customer no existe");
	}
	
	@Test
	@Transactional
	@Order(3)
	void update() {
		
		Optional<Customer> customerOptional = customerRepository.findById(email);
		
		assertTrue(customerOptional.isPresent(),"El customer no existe");
		
		Customer customer=customerOptional.get();
		
		customer.setEnable("Y");
		
		customerRepository.save(customer);
	}
	
	@Test
	@Transactional
	@Order(4)
	void delete() {
		
		Optional<Customer> customerOptional = customerRepository.findById(email);
		
		assertTrue(customerOptional.isPresent(),"El customer no existe");
		
		Customer customer=customerOptional.get();
		
		customerRepository.delete(customer);
	}
	
	@Test
	@Transactional
	@Order(5)
	void findAll() {
		customerRepository.findAll().forEach(customer->{
			log.info("Name: "+customer.getName());
			log.info("Email: "+customer.getAddress());
		});
	
		

	}
	
	@Test
	@Transactional
	@Order(6)
	void count() {
		//total de filas que trae la consulta
		log.info("Count: "+customerRepository.count());
	
		

	}
	
	@Test
	@Transactional
	@Order(7)
	void findByEnableAndEmail() {
		
		List<Customer> customers=customerRepository.findByEnableAndEmail("Y", email);
		assertFalse(customers.isEmpty());
		customers.forEach(customer ->{
			log.info("Name: "+customer.getName());
			log.info("Email: "+customer.getEmail());
		});

	}

	
	@Test
	@Transactional
	@Order(8)
	void findCustomerLikemar() {
		
		List<Customer> customers=customerRepository.findCustomerLikemar();
		assertFalse(customers.isEmpty());
		customers.forEach(customer ->{
			log.info("Name: "+customer.getName());
			log.info("Email: "+customer.getEmail());
		});

	}
	
	
	
}
