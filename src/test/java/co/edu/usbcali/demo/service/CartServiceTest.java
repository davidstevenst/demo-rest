package co.edu.usbcali.demo.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
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
	
	
	@Test
	void debeCrearUnShoppingCart() throws Exception {
		//Arrange
		String email="abaglowbn@furl.net";
		ShoppingCart shoppingCart= null;
		
		//Act
		shoppingCart=cartService.createCart(email);
		
		//Assert
		assertNotNull(shoppingCart);
	}
	


	@Test
	void debeAgregarProductShoppingCart()throws Exception {
		Integer carId=18;
		String proId="apple859";
		Integer quantity=10;
		
		ShoppingProduct shoppingProduct= null;
		shoppingProduct =cartService.addProduct(carId, proId, quantity);
		
		assertNotNull(shoppingProduct,"El shopping product es nulo");
		
	}
	
}
