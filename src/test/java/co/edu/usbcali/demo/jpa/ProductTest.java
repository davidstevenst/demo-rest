package co.edu.usbcali.demo.jpa;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.persistence.EntityManager;

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

import co.edu.usbcali.demo.domain.Product;

@SpringBootTest
@Rollback(false)
@TestMethodOrder(OrderAnnotation.class)
public class ProductTest {

	@Autowired
	EntityManager entityManager;
	
	private final static Logger log=LoggerFactory.getLogger(ProductTest.class);
	
	public final static String codigo="APPL01";	
	
	@Test
	@Transactional
	@Order(1)
	void guardar() {
		//Siga si no es nulo
		assertNotNull(entityManager, "El entityManager es nulo");
		
		Product product = entityManager.find(Product.class, codigo);
		
		//continue si NO existe el producto
		assertNull(product,"El producto con el codigo "+codigo+" YA EXISTE");
		
		
		product= new Product();
		
		product.setProId(codigo);
		product.setName("IPhone 8 plus");
		product.setPrice(1750000);
		product.setDetail("64 gb 4 ram");
		product.setImage("https://shopping-cart-usb.s3.amazonaws.com/images/iphone-11-pro-select-2019-family.jpeg");
		product.setEnable("Y");
		
		
		
		entityManager.persist(product);
		
		
	}
	
	
	@Test
	@Transactional
	@Order(2)
	void buscarporid() {
		//Siga si no es nulo
		assertNotNull(entityManager, "El entityManager es nulo");
		
		Product product = entityManager.find(Product.class, codigo);
		
		//Siga si encontró el producto
		assertNotNull(product, "El producto con codigo "+codigo+ " no existe");
		
		log.info(product.getName());
		
	}
	
	@Test
	@Transactional
	@Order(3)
	void actualizarregistro() {
		//Siga si no es nulo
		assertNotNull(entityManager, "El entityManager es nulo");
		
		Product product = entityManager.find(Product.class, codigo);
		
		assertNotNull(product,"El producto con codigo "+codigo+" no fue encontrado");
		
		product.setEnable("Y");
		
		entityManager.merge(product);
	}
	
	@Test
	@Transactional
	@Order(4)
	void eliminarregistro() {
		//Siga si no es nulo
		assertNotNull(entityManager, "El entityManager es nulo");
		
		Product product = entityManager.find(Product.class, codigo);
		//siga si no es nulo
		assertNotNull(product, "El producto con codigo "+codigo+" no fue encontrado");
		
		entityManager.remove(product);
		
	}
	
}
