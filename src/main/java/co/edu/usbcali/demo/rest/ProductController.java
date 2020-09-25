package co.edu.usbcali.demo.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.demo.domain.Product;
import co.edu.usbcali.demo.dto.ProductDTO;
import co.edu.usbcali.demo.mapper.ProductMapper;
import co.edu.usbcali.demo.repository.ProductRepository;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	private final static Logger log =LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	ProductRepository productrepository;
	
	@Autowired
	ProductMapper productMapper;
	
	
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody ProductDTO productDTO){
		
		try {
			Product product = productMapper.toProduct(productDTO);
			productrepository.save(product);
			productDTO=productMapper.toProductDTO(product);
			return ResponseEntity.ok().body(productDTO);
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			return ResponseEntity.ok().body(e.getMessage());
		}
		
	}
	
	@GetMapping("/findAll")
	public ResponseEntity<?> findAll(){
		try {
			List<Product> products = productrepository.findAll();
			List<ProductDTO> productDTOs=productMapper.toProductsDTO(products);
			
			return ResponseEntity.ok().body(productDTOs);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.ok().body(e.getMessage());
		}
	}
	
	
	@GetMapping("/findById/{proid}")
	public ResponseEntity<?> findById(@PathVariable("proid") String proid){
		
		try {
			
			Optional<Product> productOptional=productrepository.findById(proid);
			if (productOptional.isPresent()==false) {
				return ResponseEntity.ok().body("Product encontrado");
			}
			Product product = productOptional.get();
			
			ProductDTO productDTO =productMapper.toProductDTO(product);
			
			return ResponseEntity.ok().body(productDTO);
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			return ResponseEntity.ok().body(e.getMessage());
		}
		
		
	
	}
	
	
	
	
}
