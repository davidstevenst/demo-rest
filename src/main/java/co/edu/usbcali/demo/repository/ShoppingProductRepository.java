package co.edu.usbcali.demo.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbcali.demo.domain.ShoppingProduct;

public interface ShoppingProductRepository extends JpaRepository<ShoppingProduct, Integer> {
	
	@Query("SELECT SUM(shpr.total) FROM ShoppingProduct shpr WHERE shpr.shoppingCart.carId=:carId")
	public Long totalShoppingProductByShoppingCart(Integer carId);

	@Query("SELECT shpr.shprId FROM ShoppingProduct shpr WHERE shpr.shoppingCart.carId=:carId and shpr.product.proId=:proId ")
	public Integer findByproIdandcarId(Integer carId, String proId);
	
	@Modifying
	@Query("DELETE FROM ShoppingProduct shpr WHERE shpr.shoppingCart.carId=:carId")
	public void deleteBycarId(Integer carId);
	
	@Query("SELECT shpr FROM ShoppingProduct shpr WHERE shpr.shoppingCart.carId=:carId")
	public List<ShoppingProduct> findByCarId(Integer carId);
	
	@Query("SELECT shpr.shprId FROM ShoppingProduct shpr WHERE shpr.shoppingCart.carId=:carId AND shpr.product.proId=:proId")
	public Integer findProductInShoppingCart(Integer carId, String proId);
	
	@Query("SELECT shpr FROM ShoppingProduct shpr WHERE shpr.shoppingCart.customer.email=:email AND shpr.shoppingCart.enable='P'")
	public List<ShoppingProduct> findCompras(String email);
	
	
	
	
}