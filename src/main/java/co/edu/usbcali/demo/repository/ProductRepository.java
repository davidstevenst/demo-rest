package co.edu.usbcali.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.usbcali.demo.domain.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
	
	
	public List<Product> findByEnableAndProId(String enable, String proid);
	
	
	@Query("SELECT pro FROM Product pro WHERE pro.name LIKE 'iP%'")
	public List<Product> findProductLikeiP();
	
	
	@Query("SELECT pro FROM Product pro WHERE pro.price > 3000000")
	public List<Product> findProductpreciomayora();
	
	@Query("SELECT pro FROM Product pro WHERE pro.proId LIKE 'APPL%' and pro.enable='Y'")
	public List<Product> findProductLikeApplAndY();

	@Query("SELECT pro FROM Product pro WHERE pro.name='iPhone 8 plus' and enable='Y'")
	public List<Product> findproductwherenameiphone();
	
	public List<Product> findByNameContainingOrDetailContaining(String name,String detail);
	
	public List<Product> findByEnable(String enable);
	
	
	@Query("SELECT pro FROM Product pro WHERE pro.enable='Y'")
	public List<Product> findByEnableY();
	
	
	@Query("SELECT pro FROM Product pro WHERE pro.name LIKE %:busqueda% OR pro.detail LIKE %:busqueda% AND pro.enable='Y'")
	public List<Product> findBusqueda(@Param("busqueda") String busqueda);
	
	
}
