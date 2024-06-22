package com.jewelbankers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelbankers.entity.Customer;
import com.jewelbankers.repository.CustomerRepository;

@Service
public class CustomerService {

	
	@Autowired
	private CustomerRepository customerRepository;
	
	public List<Customer> findCustomersByName(String customerName) {
		//String customername="%"+CustomerName+"%";
		//String customername=customerName;
		//System.out.println(customerName);		
        //return customerRepository.findByStreetIgnoreCaseContaining(customername);
        return customerRepository.findByCustomerNameIgnoreCaseContaining(customerName);
        
    }
	

    public CustomerService() {
        //this.customers = buildFakeCustomers();
    }

	/*
	 * private List<Customer> buildFakeCustomers() { Customer c1 = new
	 * Customer(1L,"John Doe", "john@example.com", "1234567890"); Customer c2 = new
	 * Customer(2L,"Jane Smith", "jane@example.com", "0987654321"); Customer c3 =
	 * new Customer(3L,"Michael Johnson", "michael@example.com", "1122334455");
	 * Customer c4 = new Customer(4L,"Patricia Brown", "patricia@example.com",
	 * "6677889900"); Customer c5 = new Customer(5L,"Linda Davis",
	 * "linda@example.com", "2233445566");
	 * 
	 * return List.of(c1, c2, c3, c4, c5); }
	 */

    public List<Customer> findAll() {
    	return customerRepository.findAll();
        //return this.customers;
    }

	
	  public Optional<Customer> findById(Long id) { return this.customerRepository.findById(id); }

	public Customer saveCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return customerRepository.save(customer);
	}

	public String deleteCustomer(Long id) {
		// TODO Auto-generated method stub
		if (customerRepository.existsById(id)) {
		    customerRepository.deleteById(id);
		    return("Sucessfully deleted for Customer Id "+id);

		} else {
		     return("Customer ID is not found");
		}
		
	}

	public Customer updateCustomer(Long id, Customer customer) {
		// TODO Auto-generated method stub
		 Customer existingCustomer = customerRepository.findById(id).orElse(null);
	        if (existingCustomer != null) {
	            existingCustomer.setCustomerName(customer.getCustomerName());
	            existingCustomer.setAddress(customer.getAddress());
	            existingCustomer.setStreet(customer.getStreet());
	            existingCustomer.setArea(customer.getArea());
	            existingCustomer.setCountry(customer.getCountry());
	            existingCustomer.setDistrict(customer.getDistrict());
	            existingCustomer.setEmail(customer.getEmail());
	            existingCustomer.setPhone(customer.getPhone());
	            existingCustomer.setPincode(customer.getPincode());
	            existingCustomer.setRelationShip(customer.getRelationShip());
	            existingCustomer.setRelationShipName(customer.getRelationShipName());
	           // existingCustomer.setEmail(customer.getEmail());
	            // Update other fields as needed
	            return customerRepository.save(existingCustomer);
	        } else {
	            // Handle case where customer is not found, e.g., throw an exception
	            return null;
	        }
		//return null;
	}
	 
}
