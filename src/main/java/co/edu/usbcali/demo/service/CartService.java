package co.edu.usbcali.demo.service;

import java.util.List;
import java.util.Optional;

import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.domain.ShoppingProduct;

public interface CartService {
	
	
	public Optional<ShoppingCart> findCart(Integer carId) throws Exception;
	public ShoppingCart createCart(String email)throws Exception;
	public ShoppingProduct addProduct(Integer carId,String proId,Integer quantity )throws Exception;
	public void removeProduct(Integer carId,String proId)throws Exception;
	public void clearCart(Integer carId)throws Exception;
	public List<ShoppingProduct> findShoppingProductByShoppingCart(Integer carId)throws Exception;
	public Optional<ShoppingCart> findShoppingCartActive(String email) throws Exception;
	public void updateCarroPagado(Integer carId, Integer payId) throws Exception;
	public List<ShoppingProduct> findCompras(String email) throws Exception;
	
	
	
} 	