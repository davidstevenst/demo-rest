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
import co.edu.usbcali.demo.domain.Product;
import co.edu.usbcali.demo.repository.ProductRepository;


@Rollback(false)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class ProductServiceTest {
	
	private final static Logger log=LoggerFactory.getLogger(ProductServiceTest.class);
	
	private final static String proId="APPL01";
	
	@Autowired
	ProductService productService;
	
	@Test
	@Order(1)
	void save() throws Exception {
		
		Optional<Product> productOptional = productService.findById(proId);
		
		Product product = new Product();
		
		product.setProId(proId);
		product.setName("IPhone 8 plus");
		product.setPrice(1750000);
		product.setDetail("64 gb 4 ram");
		product.setImage("https://shopping-cart-usb.s3.amazonaws.com/images/iphone-11-pro-select-2019-family.jpeg");
		product.setEnable("Y");
		
		productService.save(product);
	}
	
	
	@Test
	@Order(2)
	void findById() throws Exception {
		
		Optional<Product> productOptional = productService.findById(proId);
		
		assertTrue(productOptional.isPresent(),"El product no existe");
	}
	
	@Test
	@Order(3)
	void update() throws Exception {
		
		Optional<Product> productOptional = productService.findById(proId);
		
		assertTrue(productOptional.isPresent(),"El product no existe");
		
		Product product=productOptional.get();
		
		product.setEnable("N");
		
		productService.update(product);
	}
	
	@Test
	@Order(4)
	void delete() throws Exception {
		
		Optional<Product> productOptional = productService.findById(proId);
		
		assertTrue(productOptional.isPresent(),"El product no existe");
		
		Product product=productOptional.get();
		
		productService.delete(product);
	}
	
	@Test

	@Order(5)
	void findAll() {
		productService.findAll().forEach(customer->{
			log.info("Name: "+customer.getName());
			log.info("Price: "+customer.getPrice());
		});
	

	}
	
	@Test
	@Order(6)
	void count() {
		//total de filas que trae la consulta
		log.info("Count: "+productService.count());
	
		

	}
	
	
}
