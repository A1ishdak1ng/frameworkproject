package ru.datamanagment.roadTransportCopany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.datamanagment.roadTransportCopany.exeption.ResourceNotFoundException;
import ru.datamanagment.roadTransportCopany.model.Customer;
import ru.datamanagment.roadTransportCopany.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;


    @GetMapping
    public String getAllCustomer(Model model) throws ResourceNotFoundException {


        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("newCustomer", new Customer());
        return "customers";
    }

    @PostMapping
    public String createCustomer(@ModelAttribute("newCustomer") Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return "redirect:/customers";
    }

    @PostMapping("/{id}")
    public String updateCustomer(@PathVariable Long id, @RequestBody Customer customer) throws ResourceNotFoundException {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        //TODO
        return "customers";
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return "customers";
    }

}
