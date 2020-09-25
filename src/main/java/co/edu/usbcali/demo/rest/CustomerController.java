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

import co.edu.usbcali.demo.domain.Customer;
import co.edu.usbcali.demo.dto.CustomerDTO;
import co.edu.usbcali.demo.mapper.CustomerMapper;
import co.edu.usbcali.demo.repository.CustomerRepository;

@RestController					 	//le dice que esto es un servicio rest
@RequestMapping("/api/customer") 	//esto me dice como se va a llamar
public class CustomerController {

	private final static Logger log =LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CustomerMapper customerMapper;

	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody CustomerDTO customerDTO){
		try {
			
			Customer customer = customerMapper.toCustomer(customerDTO);
			customerRepository.save(customer);
			customerDTO=customerMapper.toCustomerDTO(customer);
			return ResponseEntity.ok().body(customerDTO);
						
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	
	
	@GetMapping("/findAll")
	public ResponseEntity<?> findAll(){
		try {
			List<Customer> customers =customerRepository.findAll();
			List<CustomerDTO> customerDTOs = customerMapper.toCustomersDTO(customers);
			
			return ResponseEntity.ok().body(customerDTOs);
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ResponseEntity.ok().body(e.getMessage());
		}
	}
	
	
	
	@GetMapping("/findById/{email}")
	public ResponseEntity<?> findById(@PathVariable("email") String email) {
		
		try {
			Optional<Customer> customerOptional=customerRepository.findById(email);
			if (customerOptional.isPresent()==false) {
				return ResponseEntity.ok().body("Customer not found");
			}
			Customer customer = customerOptional.get();
			
			//Paso la informacion del entity a el dto
			CustomerDTO customerDTO= customerMapper.toCustomerDTO(customer);

	
			return ResponseEntity.ok().body(customerDTO);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(),e);
			return ResponseEntity.ok().body(e.getMessage());
		}
		
	}
	
	
}
