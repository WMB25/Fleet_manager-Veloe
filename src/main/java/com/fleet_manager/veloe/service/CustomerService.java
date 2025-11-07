package com.fleet_manager.veloe.service;

import com.fleet_manager.veloe.model.Customer;
import com.fleet_manager.veloe.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer){
        if(customerRepository.existsByDocument(customer.getDocument())){
            throw new IllegalArgumentException("Cliente já cadastrado com este documento: "
                    + customer.getDocument());
        }

        return customerRepository.save(customer);
    }

    @Cacheable("customers")
    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    @Transactional
    public Optional<Customer> findById(Long id){
        return customerRepository.findById(id);
    }

    @Transactional
    public Optional<Customer> findByDocument(String document){
        return customerRepository.findByDocument(document);
    }

    public Customer updateCustomer(Long id, @NotNull Customer customerDetails){
        Customer customer = customerRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));

        if(!customer.getDocument().equals(customerDetails.getDocument()) && customerRepository.existsByDocument(customerDetails.getDocument())){
            throw new IllegalArgumentException("Já existe um cliente cadastrado com documento: "
                    + customerDetails.getDocument());
        }

        customer.setName(customerDetails.getName());
        customer.setDocument(customerDetails.getDocument());
        customer.setEmail(customerDetails.getEmail());
        customer.setPhone(customerDetails.getPhone());

        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        if(!customerRepository.existsById(id)){
            throw new IllegalArgumentException("Cliente não encontrado com ID: " + id);
        }
        customerRepository.deleteById(id);
    }
}