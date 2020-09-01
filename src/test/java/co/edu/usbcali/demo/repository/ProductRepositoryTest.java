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
import org.springframework.data.mapping.AccessOptions.GetOptions.GetNulls;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.Customer;
import co.edu.usbcali.demo.domain.Product;


@Rollback(false)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class ProductRepositoryTest {
	
	private final static Logger log=LoggerFactory.getLogger(ProductRepositoryTest.class);
	
	private final static String codigo="APPL01";	
	
	@Autowired
	
	ProductRepository productRepository;
	
	
	@Test
	@Transactional
	@Order(1)
	void save() {
		
		Optional<Product> productOptional = productRepository.findById(codigo);
		
		//Siga si es falso quiere decir que no existe
		assertFalse(productOptional.isPresent(),"El producto ya existe");
		
		Product product = new Product();
		
		product.setProId(codigo);
		product.setName("IPhone 8 plus");
		product.setPrice(1750000);
		product.setDetail("64 gb 4 ram");
		product.setImage("https://shopping-cart-usb.s3.amazonaws.com/images/iphone-11-pro-select-2019-family.jpeg");
		product.setEnable("Y");
		
		productRepository.save(product);
	}
	
	@Test
	@Transactional
	@Order(2)
	void findById() {
		
		Optional<Product> productOptional = productRepository.findById(codigo);
		
		//siga si lo encontró
		assertTrue(productOptional.isPresent(), "El producto con codigo "+codigo+" no existe");
		log.info("El producto existe");
		log.info("Nombre: "+productOptional.get().getName());
		log.info("Precio: "+productOptional.get().getPrice());
		

		
		
	}
	
	@Test
	@Transactional
	@Order(3)
	void update() {
		
		Optional<Product> productOptional = productRepository.findById(codigo);
		
		assertTrue(productOptional.isPresent(), "El producto con codigo "+codigo+" no existe");
		
		Product product = productOptional.get();
		
		product.setEnable("Y");
		product.setName("iPhone 8 plus");
		
		productRepository.save(product);
		
	}
	
	@Test
	@Transactional
	@Order(4)
	void delete() {
		
		Optional<Product> productOptional = productRepository.findById(codigo);
		
		assertTrue(productOptional.isPresent(), "El producto con codigo "+codigo+" no existe");
		
		Product producto = productOptional.get();
		
		productRepository.delete(producto);
		
	}
	
	@Test
	@Transactional
	@Order(5)
	void findAll() {
		productRepository.findAll().forEach(product ->{
			log.info("Nombre= "+product.getName());
			log.info("Precio= "+product.getPrice());
		});
	}
	
	@Test
	@Transactional
	void count() {
		log.info("Count: "+productRepository.count());
	}
	
	@Test
	@Transactional
	@Order(6)
	void findByEnableAndCode() {
		
		List<Product> product=productRepository.findByEnableAndProId("Y",codigo);
		assertFalse(product.isEmpty());
		product.forEach(products ->{
			log.info("Name= "+products.getName());
			log.info("Price= "+products.getPrice());
		});
		
		
	}
	
	@Test
	@Transactional
	@Order(7)
	void findproductlikeiP() {
		List<Product> product = productRepository.findProductLikeiP();
		assertFalse(product.isEmpty());
		product.forEach(products ->{
			log.info("Name= "+products.getName());
		});
	}
	
	@Test
	@Transactional
	@Order(8)
	void findproductmayora() {
		List<Product> product = productRepository.findProductpreciomayora();
		assertFalse(product.isEmpty());
		product.forEach(products ->{
			log.info("Name= "+products.getName());
		});
	}
	
	@Test
	@Transactional
	@Order(9)
	void findbylikeandy() {
		List<Product> product = productRepository.findProductLikeApplAndY();
		assertFalse(product.isEmpty());
		product.forEach(products ->{
			log.info("Name= "+products.getName());
		});
	}
	
	@Test
	@Transactional
	@Order(10)
	void findproductwherenameiphone() {
		
		List<Product> product = productRepository.findproductwherenameiphone();
		assertFalse(product.isEmpty());
		product.forEach(products ->{
			log.info("Name= "+products.getName());
		});
		
	}
	
	@Test
	@Transactional
	@Order(11)
	void findByNameOrDetail() {
		
		List<Product> product=productRepository.findByNameContainingOrDetailContaining("ne","ne");
		assertFalse(product.isEmpty());
		product.forEach(products ->{
			log.info("Name= "+products.getName());
			log.info("Price= "+products.getPrice());
		});
		
		
	}
	
	@Test
	@Transactional
	@Order(12)
	void findByEnable() {
		String enable="Y";
		List<Product> product=productRepository.findByEnable(enable);
		assertFalse(product.isEmpty());
		product.forEach(products ->{
			log.info("Name= "+products.getName());
			log.info("Price= "+products.getPrice());
		});
	}
}
