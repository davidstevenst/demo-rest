package co.edu.usbcali.demo.jpql;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.usbcali.demo.domain.Customer;



@SpringBootTest
class TestCustomerJPQL {

	@Autowired
	EntityManager entityManager;
	
	private final static Logger log=LoggerFactory.getLogger(TestCustomerJPQL.class);
	
	@BeforeEach
	public void beforeEach() {
		log.info("Before Each..");
		assertNotNull(entityManager, "El entityManager es nulo");
	}
	
	
	@Test
	public void selectLike() {
		log.info("selectLike");
		String jpql=" SELECT cus FROM Customer cus WHERE cus.name LIKE 'Ma%'";
		
		List<Customer> customers=entityManager.createQuery(jpql,Customer.class).getResultList();
		
		

		
		customers.forEach(customer->{
			log.info(customer.getEmail());
			log.info(customer.getName());
		});
	}
	
	@Test
	public void selectWhereEnable() {
		log.info("select where");
		String jpql=" SELECT cus FROM Customer cus WHERE cus.enable='Y'";
		
		List<Customer> customers=entityManager.createQuery(jpql,Customer.class).getResultList();
		
		customers.forEach(customer->{
			log.info(customer.getEmail());
			log.info(customer.getName());
			log.info(customer.getEnable());
		});
	}
	
	@Test
	public void selectWhereParam() {
		log.info("select where param");
		String jpql=" SELECT cus FROM Customer cus WHERE cus.enable=:enable";
		
		List<Customer> customers=entityManager.
				createQuery(jpql,Customer.class).
				setParameter("enable", "Y").
				getResultList();
		
		customers.forEach(customer->{
			log.info(customer.getEmail());
			log.info(customer.getName());
			log.info(customer.getEnable());
		});
	}
	
	@Test
	public void selectAll() {
		log.info("selectAll");
		String jpql=" SELECT cus FROM Customer cus";
		
		List<Customer> customers=entityManager.createQuery(jpql,Customer.class).getResultList();
		
		

		
		customers.forEach(customer->{
			log.info(customer.getEmail());
			log.info(customer.getName());
		});
	}
	

}
