package co.edu.usbcali.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.usbcali.demo.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {
	
	
	public List<Customer> findByEnableAndEmail(String enable, String email);
	
	
	
	@Query("SELECT cus FROM Customer cus WHERE cus.name LIKE 'Mar%'")
	public List<Customer> findCustomerLikemar();
	
	
	@Query("SELECT cus.email FROM Customer cus WHERE cus.email=:email AND cus.token =:token AND cus.enable='Y'")
	public Optional<Customer> loginCustomerByEmailAndToken(String email, String token);
	
	
}
