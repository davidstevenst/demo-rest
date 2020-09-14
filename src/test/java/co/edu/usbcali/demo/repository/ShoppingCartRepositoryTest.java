package co.edu.usbcali.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.Customer;
import co.edu.usbcali.demo.domain.PaymentMethod;
import co.edu.usbcali.demo.domain.ShoppingCart;

@Rollback(false)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class ShoppingCartRepositoryTest {
	
	private final static Logger log=LoggerFactory.getLogger(ShoppingCartRepository.class);

	private static Integer carId = null;
	
	private static String cemail = "fgiraudot0@economist.com";
	
	private static Integer paymethodid=2;
	
	
	@Autowired
	ShoppingCartRepository shoppingcartrepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	PaymentMethodRepository paymentMethodRepository;
	
	
	@Test
	@Transactional
	@Order(1)
	void save() {
		ShoppingCart shoppingcart = new ShoppingCart();
		shoppingcart.setCarId(null);
		shoppingcart.setItems(2);
		shoppingcart.setTotal(123456789L);
		shoppingcart.setEnable("Y");
		
		Optional<Customer> customerOptional=customerRepository.findById(cemail);
		assertTrue(customerOptional.isPresent(), "El customer no existe");
		Customer customer = customerOptional.get();
		shoppingcart.setCustomer(customer);
		
		Optional<PaymentMethod> paymentMethodOptional=paymentMethodRepository.findById(paymethodid);
		assertTrue(paymentMethodOptional.isPresent(), "El payment method no existe");
		PaymentMethod paymentmethod = paymentMethodOptional.get();
		
		shoppingcart.setPaymentMethod(paymentmethod);
		
		shoppingcart=shoppingcartrepository.save(shoppingcart);
		
		carId=shoppingcart.getCarId();
		
		assertNotNull(carId, "El carId es nulo");
		
		
	}
	
	@Test
	@Transactional
	@Order(2)
	void findbyid() {
		Optional<ShoppingCart> shoppingcartoptional= shoppingcartrepository.findById(carId);
		
		assertTrue(shoppingcartoptional.isPresent(), "El shoppingcart no existe");

		
	}
	
	
	@Test
	@Transactional
	@Order(3)
	void update() {
		Optional<ShoppingCart> shoppingcartoptional= shoppingcartrepository.findById(carId);
		
		assertTrue(shoppingcartoptional.isPresent(), "El shoppingcart no existe");
		
		ShoppingCart shoppingcart = shoppingcartoptional.get();
		shoppingcart.setEnable("Y");
		shoppingcartrepository.save(shoppingcart);
		
	}
	
	@Test
	@Transactional
	@Order(4)
	void delete() {
		Optional<ShoppingCart> shoppingcartoptional= shoppingcartrepository.findById(carId);
		
		assertTrue(shoppingcartoptional.isPresent(), "El shoppingcart no existe");
		
		ShoppingCart shoppingcart = shoppingcartoptional.get();
		shoppingcartrepository.delete(shoppingcart);

		
	}

}
