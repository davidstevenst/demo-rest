package co.edu.usbcali.demo.service;

import static org.junit.jupiter.api.Assertions.*;

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

import co.edu.usbcali.demo.domain.PaymentMethod;
import co.edu.usbcali.demo.domain.ShoppingCart;





@Rollback(false)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class PaymentMethodServiceTest {

	private final static Logger log=LoggerFactory.getLogger(PaymentMethodServiceTest.class);
	
	private static Integer payId=null;
	
	@Autowired
	PaymentMethodService paymentMethodService;
	
	
	
	@Test
	@Order(1)
	void save() throws Exception {
		PaymentMethod paymentMethod= new PaymentMethod();
		
		paymentMethod.setName("Banco popular");
		paymentMethod.setEnable("Y");

		
		paymentMethodService.save(paymentMethod);
		payId = paymentMethod.getPayId();
	
	}
	
	@Test
	@Order(2)
	void findById() throws Exception {
		
		Optional<PaymentMethod> paymentmethodoptional= paymentMethodService.findById(payId);
	
		
		log.info("Nombre "+paymentmethodoptional.get().getName());
		

		
	}
	
	@Test
	@Order(3)
	void update() throws Exception {
		assertTrue(paymentMethodService.findById(payId).isPresent());
		PaymentMethod paymentMethod = paymentMethodService.findById(payId).get();
		paymentMethod.setEnable("N");
		paymentMethodService.update(paymentMethod);
		
		
	}
	
	@Test
	@Order(4)
	void delete() throws Exception {
		assertTrue(paymentMethodService.findById(payId).isPresent());
		PaymentMethod paymentMethod = paymentMethodService.findById(payId).get();
		paymentMethodService.delete(paymentMethod);
		
		
	}
	

	@Test
	@Order(5)
	void count() throws Exception {
		log.info("Count: "+paymentMethodService.count());
		
		
	}
	
	

	
	
	
}
