package co.edu.usbcali.demo.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;


import co.edu.usbcali.demo.domain.Customer;
import co.edu.usbcali.demo.domain.PaymentMethod;
import co.edu.usbcali.demo.domain.ShoppingCart;


@Rollback(false)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class ShoppingCartServiceTest {
	  
	private final static Logger log=LoggerFactory.getLogger(ShoppingCartServiceTest.class);
	
	
	@Autowired
	ShoppingCartService shoppingCartService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	PaymentMethodService paymentMethodService;
	
	private static Integer carId = null;
	
	private static String cemail = "aeicke16@weibo.com";
	
	private static Integer paymethodid=2;
	
	@Test
	@Order(1)
	void save() throws Exception {
		ShoppingCart shoppingcart = new ShoppingCart();
		shoppingcart.setCarId(null);
		shoppingcart.setItems(2);
		shoppingcart.setTotal(123456789L);
		shoppingcart.setEnable("Y");
		
		Optional<Customer> customerOptional=customerService.findById(cemail);
		Customer customer = customerOptional.get();
		shoppingcart.setCustomer(customer);
		
		Optional<PaymentMethod> paymentMethodOptional=paymentMethodService.findById(paymethodid);
		PaymentMethod paymentmethod = paymentMethodOptional.get();
		
		shoppingcart.setPaymentMethod(paymentmethod);
		
		shoppingcart=shoppingCartService.save(shoppingcart);
		
		carId=shoppingcart.getCarId();
		
		
		
	}
	
	@Test
	@Order(2)
	void findbyid() throws Exception {
		Optional<ShoppingCart> shoppingcartoptional= shoppingCartService.findById(carId);
		
		log.info("Total "+shoppingcartoptional.get().getTotal());
		
	}
	
	
	@Test
	@Order(3)
	void update() throws Exception {
		Optional<ShoppingCart> shoppingcartoptional= shoppingCartService.findById(carId);
		
		ShoppingCart shoppingcart = shoppingcartoptional.get();
		shoppingcart.setEnable("N");
		shoppingCartService.update(shoppingcart);
		
	}
	
	@Test
	@Order(4)
	void delete() throws Exception {
		Optional<ShoppingCart> shoppingcartoptional= shoppingCartService.findById(carId);
		
	
		
		ShoppingCart shoppingcart = shoppingcartoptional.get();
		shoppingCartService.delete(shoppingcart);

		
	}
	
	@Test
	@Order(5)
	void count() {
		
		log.info("Count "+shoppingCartService.count());
	}
}
