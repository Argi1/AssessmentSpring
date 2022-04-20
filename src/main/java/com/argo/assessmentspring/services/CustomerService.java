package com.argo.assessmentspring.services;

import com.argo.assessmentspring.models.Customer;
import com.argo.assessmentspring.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    public Customer saveCustomer(Customer customer) {
        return repository.save(customer);
    }

    public List<Customer> saveCustomers(List<Customer> customers) {
        return repository.saveAll(customers);
    }

    public List<Customer> getCustomers() {
        return repository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public String deleteCustomer(Long id) {
        repository.deleteById(id);
        return "Customer removed !! " + id;
    }

    public Customer updateCustomer(Customer customer) {
        Customer existingCustomer = repository.findById(customer.getId()).orElse(null);
        if(existingCustomer == null){
            return null;
        }
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setFullName(customer.getFullName());
        existingCustomer.setTelephone(customer.getTelephone());
        existingCustomer.setRegistrationCode(customer.getRegistrationCode());
        return repository.save(existingCustomer);
    }
}
