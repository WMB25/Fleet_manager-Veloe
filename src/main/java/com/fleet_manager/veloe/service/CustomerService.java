package com.fleet_manager.veloe.service;

import com.fleet_manager.veloe.model.Customer;
import com.fleet_manager.veloe.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer owner){
        if(customerRepository.existByDocument(owner.getDocument())){
            throw new IllegalArgumentException("Cliente já cadastrado com este documento: " + owner.getDocument());
        }

        return customerRepository.save(owner);
    }

    @Transactional
    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    @Transactional
    public Optional<Customer> findById(Long id){
        return customerRepository.findById(id);
    }

    @Transactional
    public Optional<Customer> findByDocument(String document){
        return customerRepository.findByLicenseDrive(document);
    }

    public Customer updateCustomer(Long id, @NotNull Customer customerDetails){
        Customer owner = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));

        if(!owner.getDocument().equals(customerDetails.getDocument()) && customerRepository.existByDocument(customerDetails.getDocument())){
            throw new IllegalArgumentException("Já existe um cliente cadastrado co este documento: " + customerDetails.getDocument());
        }

        owner.setName(customerDetails.getName());
        owner.setDocument(customerDetails.getDocument());
        owner.setEmail(customerDetails.getEmail());
        owner.setPhone(customerDetails.getPhone());

        return customerRepository.save(owner);
    }

    public void deleteCustomer(Long id) {
        if(!customerRepository.existsById(id)){
            throw new IllegalArgumentException("Cliente não encontrado como ID: " + id);
        }
        customerRepository.deleteById(id);
    }
}