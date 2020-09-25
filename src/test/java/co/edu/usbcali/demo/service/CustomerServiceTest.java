package co.edu.usbcali.demo.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
class CustomerServiceTest {
	private final static Logger log=LoggerFactory.getLogger(CustomerServiceTest.class);
	
	private final static String email="davidstevensimbaqueva@outlook.com";
	
	@Autowired
	CustomerService customerService;
	
	
	@Test
	@Order(1)
	void save()throws Exception {
		
		Customer customer= new Customer();
		customer.setAddress("Cra 36 c # 5 b1-29");
		customer.setEmail(email);
		customer.setEnable("Y");
		customer.setName("David Steven");
		customer.setPhone("323 288 9349");
		customer.setToken("646SDFG6SD8FGSD");
		
		customerService.save(customer);
		
	}
	
	
	@Test
	@Order(2)
	void findById() throws Exception {
		
		Optional<Customer> customerOptional = customerService.findById(email);
		
		assertTrue(customerOptional.isPresent(),"El customer no existe");
	}
	
	@Test
	@Order(3)
	void update() throws Exception {
		
		Optional<Customer> customerOptional = customerService.findById(email);
		
		assertTrue(customerOptional.isPresent(),"El customer no existe");
		
		Customer customer=customerOptional.get();
		
		customer.setEnable("Y");
		
		customerService.update(customer);
	}
	
	@Test
	@Order(4)
	void delete() throws Exception {
		
		Optional<Customer> customerOptional = customerService.findById(email);
		
		assertTrue(customerOptional.isPresent(),"El customer no existe");
		
		Customer customer=customerOptional.get();
		
		customerService.delete(customer);
	}
	
	@Test
	@Order(5)
	void findAll() {
		customerService.findAll().forEach(customer->{
			log.info("Name: "+customer.getName());
			log.info("Email: "+customer.getAddress());
		});
	

	}
	
	@Test
	@Order(6)
	void count() {
		//total de filas que trae la consulta
		log.info("Count: "+customerService.count());
	
		

	}
	

}
