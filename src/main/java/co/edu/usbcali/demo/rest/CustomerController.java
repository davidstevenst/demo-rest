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

import co.edu.usbcali.demo.domain.Customer;
import co.edu.usbcali.demo.dto.CustomerDTO;
import co.edu.usbcali.demo.mapper.CustomerMapper;
import co.edu.usbcali.demo.service.CustomerService;

@RestController					 	//le dice que esto es un servicio rest
@RequestMapping("/api/customer") 	//esto me dice como se va a llamar

@CrossOrigin("*")
public class CustomerController {

	private final static Logger log =LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CustomerMapper customerMapper;

	@PostMapping("/save")
	public ResponseEntity<?> save(@Valid @RequestBody CustomerDTO customerDTO) throws Exception{

			
			Customer customer = customerMapper.toCustomer(customerDTO);
			customerService.save(customer);
			customerDTO=customerMapper.toCustomerDTO(customer);
	

			return ResponseEntity.ok().body(customerDTO);
		
	}

	
	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody CustomerDTO customerDTO) throws Exception{

			
			Customer customer = customerMapper.toCustomer(customerDTO);
			customerService.update(customer);
			customerDTO=customerMapper.toCustomerDTO(customer);
	
						

			return ResponseEntity.ok().body(customerDTO);
		
	}
	
	
	
	@GetMapping("/findAll")
	public ResponseEntity<?> findAll()throws Exception{
	
			List<Customer> customers =customerService.findAll();
			List<CustomerDTO> customerDTOs = customerMapper.toCustomersDTO(customers);
			
			return ResponseEntity.ok().body(customerDTOs);
	
	}
	
	
	@GetMapping("/findById/{email}")
	public ResponseEntity<?> findById(@PathVariable("email") String email) throws Exception {
	
			Optional<Customer> customerOptional=customerService.findById(email);
			if (customerOptional.isPresent()==false) {
				return ResponseEntity.ok().body("Customer not found");
			}
			Customer customer = customerOptional.get();
			
			//Paso la informacion del entity a el dto
			CustomerDTO customerDTO= customerMapper.toCustomerDTO(customer);
	
			return ResponseEntity.ok().body(customerDTO);
	
	}
	
	@DeleteMapping("/delete/{email}")
	public ResponseEntity<?> delete(@PathVariable("email") String email) throws Exception {
		
			customerService.deleteById(email);

			return ResponseEntity.ok().build();
		
	}
	
	@GetMapping("/loginCustomerAuth/{email}/{token}")
	public ResponseEntity<?> loginCustomerByEmailAndToken(@PathVariable("email") String email,
														  @PathVariable("token") String token) throws Exception {
		
		customerService.loginCustomerByEmailAndToken(email, token);

		return ResponseEntity.ok().body(customerService.loginCustomerByEmailAndToken(email, token));
	
	}
	
	@PostMapping("/loginCustomerAuth")
	public ResponseEntity<?> loginCustomerByEmailAndToken(@RequestBody CustomerDTO customerDTO) throws Exception {
		
		
		return ResponseEntity.ok().body(customerService.loginCustomerByEmailAndToken(customerDTO.getEmail(), customerDTO.getToken()));
	
	}
	
	
}
