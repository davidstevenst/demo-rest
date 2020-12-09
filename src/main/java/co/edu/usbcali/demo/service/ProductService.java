package co.edu.usbcali.demo.service;

import java.util.List;

import co.edu.usbcali.demo.domain.Product;

public interface ProductService extends GenericService<Product, String>{
	
	public List<Product> findAllEnable();
	
	public List<Product> findBusqueda(String busqueda) throws Exception;
	
	
	
}
