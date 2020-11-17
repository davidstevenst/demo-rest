package co.edu.usbcali.demo.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.domain.ShoppingProduct;


@SpringBootTest
@Rollback(false)
class CartServiceTest {

	@Autowired
	CartService cartService;
	
	private final static Logger log=LoggerFactory.getLogger(CartServiceTest.class);
	
	@Test
	void debeCrearUnShoppingCart() throws Exception {
		//Arrange
		String email="fmedgwick4h@360.cn";
		ShoppingCart shoppingCart= null;
		
		//Act
		shoppingCart=cartService.createCart(email);
		
		//Assert
		assertNotNull(shoppingCart);
	}
	


	@Test
	void debeAgregarProductShoppingCart()throws Exception {
		Integer carId=32;
		String proId="APPL05";
		Integer quantity=10;
		
		ShoppingProduct shoppingProduct= null;
		shoppingProduct =cartService.addProduct(carId, proId, quantity);
		
		assertNotNull(shoppingProduct,"El shopping product es nulo");
		
	}
	
	
	@Test
	void eliminaShoppingProduct() throws Exception{
		
		Integer carId=32;
		String proId="apple859";
		
		cartService.removeProduct(carId, proId);
		
		
		
	}
	
	
	@Test
	void eliminarProductosDeCarro()throws Exception{
		Integer carId=32;
		
		cartService.clearCart(carId);
		
	}
	
	
	@Test
	void findByCarId()throws Exception{
		Integer carId=32;
		
		cartService.findShoppingProductByShoppingCart(carId).forEach(shoppingProduct ->{
			log.info("Id de shopping Product: " +shoppingProduct.getShprId()
					+" Total "+shoppingProduct.getQuantity());
		});;
		
		
	}
	
	@Test
	void findShoppingCartActive()throws Exception{
		//String email="tknoles29@ehow.com";
		String email="fmedgwick4h@360.cn";
		
		log.info("Id del carrito "+cartService.findShoppingCartActive(email).get());
	}
	
	
}
