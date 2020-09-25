package co.edu.usbcali.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.domain.PaymentMethod;
import co.edu.usbcali.demo.repository.PaymentMethodRepository;


@Service
@Scope("singleton")
public class PaymentMethodServiceImpl implements PaymentMethodService {

	@Autowired
	PaymentMethodRepository paymentMethodRepository;

	@Override
	@Transactional(readOnly = true)
	public List<PaymentMethod> findAll() {
	
		return paymentMethodRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED,rollbackFor = Exception.class)
	public PaymentMethod save(PaymentMethod entity) throws Exception {
		
		validate(entity);
				
		
		return paymentMethodRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED,rollbackFor = Exception.class)
	public PaymentMethod update(PaymentMethod entity) throws Exception {
		
		validate(entity);
		
		if (paymentMethodRepository.existsById(entity.getPayId()) == false) {
			throw new Exception("El id de metodo de pago con id: "+entity.getPayId()+" no existe");
		}
		
		return paymentMethodRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED,rollbackFor = Exception.class)
	public void delete(PaymentMethod entity) throws Exception {
		
		if(entity==null) {
			throw new Exception("El product es nulo");
		}
		
		if(entity.getPayId() == null || entity.getPayId()<1) {
			throw new Exception("El PaymentMethod es vacio o menor que 1");
		}
		
		if(paymentMethodRepository.existsById(entity.getPayId()) == false) {
			throw new Exception("El paymentMethod con id: "+entity.getPayId()+" no existe. No se puede borrar");
		}
		
		paymentMethodRepository.findById(entity.getPayId()).ifPresent(paymentmethod ->{
			
			if(paymentmethod.getShoppingCarts() != null && paymentmethod.getShoppingCarts().isEmpty() == false) {
				throw new RuntimeException("El paymentmethod con id:"+entity.getPayId()+" tiene shopping carts");
			}
			
		});
		
		paymentMethodRepository.deleteById(entity.getPayId());
		
		
	}

	@Override
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED,rollbackFor = Exception.class)
	public void deleteById(Integer id) throws Exception {
		if(id==null || id < 1) {
			throw new Exception("El proid es obligatorio");
		}
		
		if(paymentMethodRepository.existsById(id)) {
			delete(paymentMethodRepository.findById(id).get());
		}
		
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<PaymentMethod> findById(Integer id) throws Exception {
		
		return paymentMethodRepository.findById(id);
	}

	@Override
	public void validate(PaymentMethod entity) throws Exception {
		
		if(entity == null) {
			throw new Exception("El Payment es nulo");
		}
		
		if (entity.getName() == null || entity.getName().isBlank()) {
			throw new Exception("El nombre es nulo");
		}
		
		if (entity.getEnable() == null || entity.getEnable().isBlank()) {
			throw new Exception("El enable  es nulo");
		}
		
		
		
		
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
	
		
		return paymentMethodRepository.count();
	}

	
	
}
