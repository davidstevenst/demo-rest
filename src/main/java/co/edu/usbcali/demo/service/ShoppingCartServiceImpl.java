package co.edu.usbcali.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.ShoppingCart;
import co.edu.usbcali.demo.repository.ShoppingCartRepository;


@Service
@Scope("singleton")
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	ShoppingCartRepository shoppingCartRepository;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<ShoppingCart> findAll() {
		return shoppingCartRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED,rollbackFor = Exception.class)
	public ShoppingCart save(ShoppingCart entity) throws Exception {
		validate(entity);
		
		return shoppingCartRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED,rollbackFor = Exception.class)
	public ShoppingCart update(ShoppingCart entity) throws Exception {
		
		validate(entity);
		
		//Si no existe lanza el error
		if(shoppingCartRepository.existsById(entity.getCarId()) == false) {
					throw new Exception("El shopping cart con id: "+entity.getCarId()+" no existe");
		}
		
		
		return shoppingCartRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED,rollbackFor = Exception.class)
	public void delete(ShoppingCart entity) throws Exception {
		
		validate(entity);
		
		
		shoppingCartRepository.findById(entity.getCarId()).ifPresent(shoppingcart ->{
			if(shoppingcart.getShoppingProducts()!= null && shoppingcart.getShoppingProducts().isEmpty() == false) {
				throw new RuntimeException("El shopping cart con id: "+entity.getCarId()+" ya tiene productos incluidos (No se puede borrar)");
			}
		});
		
		shoppingCartRepository.delete(entity);
		
		
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		if(id == null || id <1) {
			throw new Exception("El id es nulo o vacio");
		}
		
		if(shoppingCartRepository.existsById(id)) {
			delete(shoppingCartRepository.findById(id).get());
		}
		
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<ShoppingCart> findById(Integer id) throws Exception {

		return shoppingCartRepository.findById(id);
	}

	@Override
	public void validate(ShoppingCart entity) throws Exception {

		if(entity == null) {
			throw new Exception("El entity es nulo");
		}
		
		if(entity.getCustomer() == null) {
			throw new Exception("El customer es nulo");
		}
	
		

		
		if(entity.getEnable() == null || entity.getEnable().isBlank()) {
			throw new Exception("El enable esta vacio o nulo");
		}
		
		if(entity.getTotal() == null) {
			throw new Exception("El total esta vacio");
		}
		
		
		
		
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
	
		return shoppingCartRepository.count();
	}

}
