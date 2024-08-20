package com.CustomerService.CustomerService.service;

import com.CustomerService.CustomerService.Repository.CustomerRepo;
import com.CustomerService.CustomerService.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public Customer getCustomerById(int id) {
        return customerRepo.findById(id).orElseThrow();
    }

    public Customer createCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    public Customer updateCustomer(int id, Customer customer) {
        Customer existingCustomer = getCustomerById(id);
        existingCustomer.setName(customer.getName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        return customerRepo.save(existingCustomer);
    }

    public void deleteCustomer(int id) {
        customerRepo.deleteById(id);
    }


}
