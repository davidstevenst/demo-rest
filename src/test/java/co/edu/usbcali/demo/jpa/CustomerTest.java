package co.edu.usbcali.demo.jpa;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.Customer;

@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)

class CustomerTest {

	private final static String email="davidstevensimbaqueva@outlook.com";
	
	private final static Logger log=LoggerFactory.getLogger(CustomerTest.class);
	
	@Autowired
	EntityManager entityManager;
	
	
	@Test
	@Transactional
	@Order(1)
	void save() {
		//Siga si no es nulo
		assertNotNull(entityManager, "El entityManager es nulo");
		Customer customer=entityManager.find(Customer.class, email);
		
		//siga si es nulo
		assertNull(customer,"El cliente con email "+email+" ya existe");
		
		customer= new Customer();
		customer.setAddress("Cra 36 c # 5 b1-29");
		customer.setEmail(email);
		customer.setEnable("Y");
		customer.setName("David Steven");
		customer.setPhone("323 288 9349");
		customer.setToken("646SDFG6SD8FGSD");
		
		//entityManager.getTransaction().begin();
		entityManager.persist(customer);
		//entityManager.getTransaction().commit();
		
	}
	
	
	@Test
	@Transactional
	@Order(2)
	void findById() {
		
		//Siga si no es nulo
		assertNotNull(entityManager, "El entityManager es nulo");
		Customer customer=entityManager.find(Customer.class, email);
				
		//siga si no es nulo
		assertNotNull(customer,"El cliente con email "+email+" no existe");
		
		log.info(customer.getName());
	}
	
	@Test
	@Transactional
	@Order(3)
	void update() {
		//Siga si no es nulo
		assertNotNull(entityManager, "El entityManager es nulo");
		Customer customer=entityManager.find(Customer.class, email);
						
		//siga si no es nulo
		assertNotNull(customer,"El cliente con email "+email+" ya existe");
		
		customer.setEnable("N");
		
		entityManager.merge(customer);
		
		
	}

	@Test
	@Transactional
	@Order(4)
	void delete() {
		//Siga si no es nulo
		assertNotNull(entityManager, "El entityManager es nulo");
		Customer customer=entityManager.find(Customer.class, email);
							
		//siga si no es nulo
		assertNotNull(customer,"El cliente con email "+email+" ya existe");
				
	
				
		entityManager.remove(customer);	
	}
	
}
