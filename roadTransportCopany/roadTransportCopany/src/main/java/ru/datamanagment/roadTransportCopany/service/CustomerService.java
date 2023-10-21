package ru.datamanagment.roadTransportCopany.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.datamanagment.roadTransportCopany.exeption.ResourceNotFoundException;
import ru.datamanagment.roadTransportCopany.model.Customer;
import ru.datamanagment.roadTransportCopany.repo.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomerById(Long id) throws ResourceNotFoundException {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customer) throws ResourceNotFoundException {
        Customer existingCustomer = getCustomerById(id);
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        existingCustomer.setAddress(customer.getAddress());
        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();

    }

    @Override
    public UserDetails loadUserByUsername(@NotNull String email) throws UsernameNotFoundException {
        return customerRepository.findCustomerByEmail(email).orElse(null);//orElseThrow(() ->
        //new UsernameNotFoundException("user " + username + " was not found!"));
    }

    public Customer findById(Long id){
        Optional<Customer> Customer = customerRepository.findById(id);
        if (Customer.isPresent()){
            return Customer.get();
        }else return null;
    }
}
