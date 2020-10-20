package co.edu.usbcali.demo.rest;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usbcali.demo.domain.PaymentMethod;

import co.edu.usbcali.demo.dto.PaymentMethodDTO;
import co.edu.usbcali.demo.mapper.PaymentMethodMapper;
import co.edu.usbcali.demo.service.PaymentMethodService;

@RestController
@RequestMapping("/api/paymentmethod")

@CrossOrigin("*")
public class PaymentMethodController {

	private final static Logger log =LoggerFactory.getLogger(PaymentMethodController.class);
	
	@Autowired
	PaymentMethodService paymentMethodService;
	
	@Autowired
	PaymentMethodMapper paymentMethodMapper;
	
	
	@PostMapping("/save")
	public ResponseEntity<?> save(@Valid @RequestBody PaymentMethodDTO paymentMethodDTO)throws Exception{
		
	
			PaymentMethod paymentMethod = paymentMethodMapper.toPaymentMethod(paymentMethodDTO);
			paymentMethodService.save(paymentMethod);
			paymentMethodDTO=paymentMethodMapper.toPaymentMethodDTO(paymentMethod);
			return ResponseEntity.ok().body(paymentMethodDTO);
			
		
		
	}
	
	@GetMapping("/findAll")
	public ResponseEntity<?> findAll()throws Exception{
		
			List<PaymentMethod> paymentMethod = paymentMethodService.findAll();
			List<PaymentMethodDTO> paymentMethodDTO=paymentMethodMapper.toPaymentMethodDTO(paymentMethod);
			
	
			return ResponseEntity.ok().body(paymentMethodDTO);
		
	}
	
	
	@GetMapping("/findById/{payId}")
	public ResponseEntity<?> findById(@PathVariable("payId") Integer payId) throws Exception{
		
	
			
			Optional<PaymentMethod> paymentMethodOptional=paymentMethodService.findById(payId);
			if (paymentMethodOptional.isPresent()==false) {
				return ResponseEntity.ok().body("paymentMethod No encontrado");
			}
			PaymentMethod paymentMethod = paymentMethodOptional.get();
			
			PaymentMethodDTO paymentMethodDTO =paymentMethodMapper.toPaymentMethodDTO(paymentMethod);
			
			return ResponseEntity.ok().body(paymentMethodDTO);
			

		
		
	
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody PaymentMethodDTO paymentMethodDTO) throws Exception{

		PaymentMethod paymentMethod = paymentMethodMapper.toPaymentMethod(paymentMethodDTO);
		paymentMethodService.update(paymentMethod);
		paymentMethodDTO=paymentMethodMapper.toPaymentMethodDTO(paymentMethod);

					

		return ResponseEntity.badRequest().body(paymentMethodDTO);
	
}
	
	@DeleteMapping("/delete/{payId}")
	public ResponseEntity<?> delete(@PathVariable("payId") Integer payId) throws Exception {
		
			paymentMethodService.deleteById(payId);

			return ResponseEntity.ok().body("Ok");
		
	}
}
