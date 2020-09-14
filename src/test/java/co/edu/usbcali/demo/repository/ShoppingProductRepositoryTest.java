package co.edu.usbcali.demo.repository;

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

import co.edu.usbcali.demo.domain.Product;
import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.domain.ShoppingProduct;


@Rollback(false)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class ShoppingProductRepositoryTest {
	
	private final static Logger log=LoggerFactory.getLogger(ShoppingProduct.class);
	
	private static Integer shprId = null;
	
	private static String proId="APPL45";
	
	private static Integer carId = 1;

	@Autowired
	ShoppingProductRepository shopProRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ShoppingCartRepository shopCartRepository;
	
	@Test
	@Transactional
	@Order(1)
	void save() {
		Integer cantidad=2;
		ShoppingProduct shoppingproduct = new ShoppingProduct();
		shoppingproduct.setQuantity(cantidad);
		
	
		
		Optional<Product> productOptional = productRepository.findById(proId);
		assertTrue(productOptional.isPresent(), "El producto no existe");
		Product product = productOptional.get();
		shoppingproduct.setProduct(product);
		
		shoppingproduct.setTotal(Long.valueOf(product.getPrice()*cantidad));
		
		Optional<ShoppingCart> shoppingcartOptional = shopCartRepository.findById(carId);
		assertTrue(shoppingcartOptional.isPresent(),"El shopping cart no existe");
		ShoppingCart shoppingcart = shoppingcartOptional.get();
		shoppingproduct.setShoppingCart(shoppingcart);
		
		shoppingproduct=shopProRepository.save(shoppingproduct);
		
		shprId= shoppingproduct.getShprId();
		
		assertNotNull(shprId,"El Shopping product es nulo");
		
	}
	
	@Test
	@Transactional
	@Order(2)
	void findById() {
		Optional<ShoppingProduct> shopproductOptional = shopProRepository.findById(shprId);
		
		assertTrue(shopproductOptional.isPresent(),"El shoppingProduct no existe");
		
		ShoppingProduct shoppingproduct = shopproductOptional.get();
		
		log.info("Producto id vendido:"+shoppingproduct.getProduct());
		log.info("Cantidad:"+shoppingproduct.getQuantity());
		log.info("Precio del producto:"+shoppingproduct.getProduct().getPrice());
		log.info("Total:"+shoppingproduct.getTotal());
	}
	
	@Test
	@Transactional
	@Order(3)
	void Update() {
		Integer productcambiar=1;
		Optional<ShoppingProduct> shopproductOptional = shopProRepository.findById(shprId);
		
		assertTrue(shopproductOptional.isPresent(),"El shoppingProduct no existe");
		
		ShoppingProduct shoppingproduct = shopproductOptional.get();
		
		shoppingproduct.setQuantity(3);
		
		shopProRepository.save(shoppingproduct);
	}
	
	@Test
	@Transactional
	@Order(4)
	void delete(){
		Optional<ShoppingProduct> shopproductOptional = shopProRepository.findById(shprId);
		
		assertTrue(shopproductOptional.isPresent(),"El shoppingProduct no existe");
		
		ShoppingProduct shoppingproduct = shopproductOptional.get();
		
		shopProRepository.delete(shoppingproduct);
	}
	

}
