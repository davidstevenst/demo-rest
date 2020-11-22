package co.edu.usbcali.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbcali.demo.domain.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

	
	@Query("SELECT shpCart.carId FROM ShoppingCart shpCart WHERE shpCart.customer.email=:email AND shpCart.enable ='Y'")
	public Optional<ShoppingCart> findShoppingCartActive(String email);
	
	
	@Modifying
	@Query("UPDATE ShoppingCart shpCart SET shpCart.paymentMethod.payId=:payId,shpCart.enable='P' WHERE shpCart.carId=:carId")
	public void updatePagarCarro(Integer carId,Integer payId);
	
}

