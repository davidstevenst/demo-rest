package co.edu.usbcali.demo.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.domain.ShoppingProduct;
import co.edu.usbcali.demo.dto.ShoppingProductDTO;
import co.edu.usbcali.demo.mapper.ShoppingCartMapper;
import co.edu.usbcali.demo.mapper.ShoppingProductMapper;
import co.edu.usbcali.demo.service.CartService;

@RestController					 	//le dice que esto es un servicio rest
@RequestMapping("/api/cart") 	//esto me dice como se va a llamar

@CrossOrigin("*")
public class CartController {
	
	private final static Logger log =LoggerFactory.getLogger(CustomerController.class);

	
	@Autowired
	CartService cartService;
	
	
	
	
	@Autowired
	ShoppingCartMapper shoppingCartMapper;
	
	@Autowired
	ShoppingProductMapper shoppingProductMapper;
	
	@GetMapping("/findByCarId/{carId}")
	public ResponseEntity<?> findByCarId(@PathVariable("carId") Integer carId)throws Exception{
	
			List<ShoppingProduct> shoppingProducts =cartService.findShoppingProductByShoppingCart(carId);
		
			List<ShoppingProductDTO> shoppingProductDTO = shoppingProductMapper.toShoppingProductsDTO(shoppingProducts);
		
		
			
			return ResponseEntity.ok().body(shoppingProductDTO);
	
	}
	
	
	@GetMapping("/createCart/{email}")
	public ResponseEntity<?> createCart(@PathVariable("email")String email) throws Exception{

		cartService.createCart(email);
		
		
		return ResponseEntity.ok().body("ok");
	
	}
	
	@GetMapping("/addProduct/{carId}/{proId}/{quantity}")
	public ResponseEntity<?> addProduct(@PathVariable("carId")Integer carId,@PathVariable("proId")String proId
										,@PathVariable("quantity")Integer quantity) throws Exception{	
		cartService.addProduct(carId, proId, quantity);
		
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/removeProduct/{carId}/{proId}")
	public ResponseEntity<?> removeProduct(@PathVariable("carId")Integer carId,@PathVariable("proId")String proId
										) throws Exception{
		
		
		cartService.removeProduct(carId, proId);
		
		
		return ResponseEntity.ok().build();
	}
	

	
	@GetMapping("/clearCart/{carId}")
	public ResponseEntity<?> clearCart(@PathVariable("carId") Integer carId) throws Exception{
		
		cartService.clearCart(carId);
		
		return ResponseEntity.ok().body("ok");
		
		
	}

	@GetMapping("/findProductByShoppingCart/{carId}")
	public ResponseEntity<?> findProductByShoppingCart(@PathVariable("carId") Integer carId) throws Exception{
		
		
		List<ShoppingProductDTO> shoppingProductDTO = shoppingProductMapper.toShoppingProductsDTO(cartService.findShoppingProductByShoppingCart(carId)); 
		
		return ResponseEntity.ok().body(shoppingProductDTO);
		
		
	}
	
	
	@GetMapping("/findShoppingCartActive/{email}")
	public ResponseEntity<?> findShoppingCartActive(@PathVariable("email") String email) throws Exception{
		
		return ResponseEntity.ok().body(cartService.findShoppingCartActive(email));
		
	}
	
	

}
