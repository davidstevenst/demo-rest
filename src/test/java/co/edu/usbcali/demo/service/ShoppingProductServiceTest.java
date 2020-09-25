package co.edu.usbcali.demo.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import co.edu.usbcali.demo.repository.ProductRepository;
import co.edu.usbcali.demo.repository.ShoppingCartRepository;
import co.edu.usbcali.demo.repository.ShoppingProductRepository;


@Rollback(false)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class ShoppingProductServiceTest {



	
private final static Logger log=LoggerFactory.getLogger(ShoppingProduct.class);
	
	private static Integer shprId = null;
	
	private static String proId="APPL45";
	
	private static Integer carId = 1;

	@Autowired
	ShoppingProductService shoppingProductService;
	
	@Autowired
	ProductService productService;
	
	
	@Autowired
	ShoppingCartService shoppingcartService;
	
	@Test
	@Order(1)
	void save() throws Exception {
		Integer cantidad=2;
		ShoppingProduct shoppingproduct = new ShoppingProduct();
		shoppingproduct.setQuantity(cantidad);
		
	
		
		Optional<Product> productOptional = productService.findById(proId);
	
		Product product = productOptional.get();
		shoppingproduct.setProduct(product);
		
		shoppingproduct.setTotal(Long.valueOf(product.getPrice()*cantidad));
		
		Optional<ShoppingCart> shoppingcartOptional = shoppingcartService.findById(carId);
		assertTrue(shoppingcartOptional.isPresent(),"El shopping cart no existe");
		ShoppingCart shoppingcart = shoppingcartOptional.get();
		shoppingproduct.setShoppingCart(shoppingcart);
		
		
		shoppingproduct=shoppingProductService.save(shoppingproduct);
		
		shprId= shoppingproduct.getShprId();
		
	}
	
	
	@Test
	@Order(2)
	void findById() throws Exception {
		Optional<ShoppingProduct> shopproductOptional = shoppingProductService.findById(shprId);
		
		assertTrue(shopproductOptional.isPresent(),"El shoppingProduct no existe");
		
		ShoppingProduct shoppingproduct = shopproductOptional.get();
		
		log.info("Cantidad:"+shoppingproduct.getQuantity());
		log.info("Total:"+shoppingproduct.getTotal());
	}
	
	
	
	@Test
	@Order(3)
	void Update() throws Exception {
		Integer productcambiar=1;
		Optional<ShoppingProduct> shopproductOptional = shoppingProductService.findById(shprId);
		
		ShoppingProduct shoppingproduct = shopproductOptional.get();
		
		shoppingproduct.setQuantity(3);
		
		shoppingProductService.save(shoppingproduct);
	}
	
	
	@Test
	@Order(4)
	void delete() throws Exception{
		Optional<ShoppingProduct> shopproductOptional = shoppingProductService.findById(shprId);
		
		
		ShoppingProduct shoppingproduct = shopproductOptional.get();
		
		shoppingProductService.delete(shoppingproduct);
	}
	
	@Test
	@Order(5)
	void count() throws Exception{

		log.info("Count: "+shoppingProductService.count());
	}
	
}
