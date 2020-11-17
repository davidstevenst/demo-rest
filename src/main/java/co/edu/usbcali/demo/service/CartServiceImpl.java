package co.edu.usbcali.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.Customer;
import co.edu.usbcali.demo.domain.Product;
import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.domain.ShoppingProduct;
import co.edu.usbcali.demo.repository.ShoppingCartRepository;

@Service
@Scope("singleton")
public class CartServiceImpl implements CartService {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	ShoppingCartService shoppingCartService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ShoppingProductService shoppingProductService;
	
	@Autowired
	ShoppingCartRepository shoppingCartRepository;

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public ShoppingCart createCart(String email) throws Exception {
		Customer customer=null;
		
		if(email==null || email.isBlank()==true) {
			throw new Exception("El email del cliente es nulo");
		}
		
		
	
		if(shoppingCartRepository.findShoppingCartActive(email).isPresent()==true) {
			
			throw new Exception("El email ya tiene un carro activo");
			
			
		}
		
		Optional<Customer> customerOptional=customerService.findById(email);
		if(customerOptional.isPresent()==false) {
			throw new Exception("No existe un customer con el email: "+email);
		}
		
		
		customer=customerOptional.get();
		
		if(customer.getEnable()==null || customer.getEnable().equals("N")==true) {
			throw new Exception("El cliente con email: "+email+" no esta habilitado");
		}
		
		ShoppingCart shoppingCart=new ShoppingCart(0, customer, null,0, 0L, "Y", null);
		
		shoppingCart=shoppingCartService.save(shoppingCart);
		
		return shoppingCart;
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public ShoppingProduct addProduct(Integer carId, String proId, Integer quantity) throws Exception {
		
		ShoppingCart shoppingCart=null;
		Product product=null;
		Long totalShoppingProduct=0L;
		Long totalShoppingCart=0L;
		
		
		
		if(carId==null || carId<=0) {
			throw new Exception("El carId es nulo o menor a cero");
		}
		
		if(proId==null || proId.isBlank()==true) {
			throw new Exception("El proId es nulo o menor a esta en blanco");
		}
		
		if(quantity==null || quantity<=0) {
			throw new Exception("El quantity es nulo o menor a cero");
		}
		
		if(shoppingCartService.findById(carId).isPresent()==false) {
			throw new Exception("El shoppingCart no existe");
		}
		
		shoppingCart=shoppingCartService.findById(carId).get();
		
		if(shoppingCart.getEnable().equals("N")==true) {
			throw new Exception("El shoppingCart esta inhabilitado");
		}
		
		if(productService.findById(proId).isPresent()==false) {
			throw new Exception("El product no existe");
		}
		
		product=productService.findById(proId).get();
		
		if(product.getEnable().equals("N")==true) {
			throw new Exception("El product esta inhabilitado");
		}
		ShoppingProduct shoppingProduct = null;
		if(shoppingProductService.findProductInShoppingCart(carId, proId) != null) {
			
			Integer shoppingProductId = shoppingProductService.findProductInShoppingCart(carId, proId);
			
			
			 shoppingProduct = shoppingProductService.findById(shoppingProductId).get();
			
			Integer cantidad = shoppingProduct.getQuantity();
			Long totalanterior= shoppingProduct.getTotal();
			shoppingProduct.setQuantity(cantidad+quantity);
			shoppingProduct.setTotal(totalanterior +Long.valueOf(product.getPrice()*quantity));
			
			shoppingProductService.update(shoppingProduct);
			
			
		}else {
			
		
		
	    shoppingProduct=new ShoppingProduct();
		shoppingProduct.setProduct(product);
		shoppingProduct.setQuantity(quantity);
		shoppingProduct.setShoppingCart(shoppingCart);
		shoppingProduct.setShprId(0);
		totalShoppingProduct=Long.valueOf(product.getPrice()*quantity);
		shoppingProduct.setTotal(totalShoppingProduct);
		
		shoppingProduct=shoppingProductService.save(shoppingProduct);
		
		totalShoppingCart=shoppingProductService.totalShoppingProductByShoppingCart(carId);
		
		shoppingCart.setTotal(totalShoppingCart);
		shoppingCartService.update(shoppingCart);
		}
		
		return shoppingProduct;
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void removeProduct(Integer carId, String proId) throws Exception {
		
		Integer shProduct=null;
		ShoppingCart shoppingCart=null;
		Product product=null;
		Long totalShoppingCart=0L;
		
		if (carId == null) {
			throw new Exception("El carId es nulo");
		}
		
		if (proId == null) {
			throw new Exception("El proId es nulo");
		}
		
		if(shoppingCartService.findById(carId).isPresent()==false) {
			throw new Exception("El shoppingCart no existe");
		}
		
		shoppingCart=shoppingCartService.findById(carId).get();
		
		if(shoppingCart.getEnable().equals("N")==true) {
			throw new Exception("El shoppingCart esta inhabilitado");
		}
		
		if (productService.findById(proId).isPresent() == false) {
			throw new Exception("El producto no existe");
		}
		
		product = productService.findById(proId).get();
		
		if(product.getEnable().equals("N")== true) {
			throw new Exception("El product esta inhabilitado");
		}
		

		
		shProduct= shoppingProductService.findByproIdandcarId(carId, proId);
		
		
		
		
		shoppingProductService.deleteById(shProduct);
		
		totalShoppingCart=shoppingProductService.totalShoppingProductByShoppingCart(carId);
		if(totalShoppingCart == null || totalShoppingCart <1) {
			totalShoppingCart =Long.valueOf(0);
		}
		shoppingCart.setTotal(totalShoppingCart);
		shoppingCartService.update(shoppingCart);
		
		
		
	}	

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public void clearCart(Integer carId) throws Exception {
		
		ShoppingCart shoppingCart = null;
		
		shoppingCart = shoppingCartService.findById(carId).get();
		
		if (shoppingCartService.findById(carId).isPresent()==false) {
			throw new Exception("El shopping cart no existe");
		}
		
		if (shoppingCart.getEnable().equals("N")) {
			throw new Exception("El shopping cart esta inhabilitado");
		}
		
		shoppingProductService.deleteByCarId(carId);
		
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<ShoppingProduct> findShoppingProductByShoppingCart(Integer carId) throws Exception {

		
		ShoppingCart shoppingCart = null;
		
		
		if(carId== null) {
			throw new Exception("El carId es obligatorio");
		}
		
		
		if (shoppingCartService.findById(carId).isPresent() == false) {
			throw new Exception("El carId no existe");
		}
		shoppingCart = shoppingCartService.findById(carId).get();
		
		
		
		if(shoppingCart.getEnable().equals("N") == true) {
			throw new Exception("El shopping cart está dehabilitado");
		}
		
		
		return shoppingProductService.findByCarId(carId);
	}

	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	public Optional<ShoppingCart> findShoppingCartActive(String email) throws Exception {
		
		if(customerService.findById(email).isPresent() == false) {
			throw new Exception("El email no existe");
		}
		
		if(shoppingCartRepository.findShoppingCartActive(email).isPresent()==false) {
			
			createCart(email);
			
			
		}
		
		
		
		return shoppingCartRepository.findShoppingCartActive(email);
	}


}