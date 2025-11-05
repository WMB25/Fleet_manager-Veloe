package com.fleet_manager.veloe.controller;

import com.fleet_manager.veloe.model.Customer;
import com.fleet_manager.veloe.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer){
        try{
            Customer savedCustomer = customerService.createCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Customer> getAllCustomer(){
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getOwnerById(@PathVariable Long id){
        Optional<Customer> owner = customerService.findById(id);
        return owner.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/documento/{document}")
    public ResponseEntity<Customer> getOwnerByDocument(@PathVariable String document){
        Optional<Customer> customer = customerService.findByDocument(document);
        return customer.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer){
        try {
            Customer updateCustomer = customerService.updateCustomer(id, customer);
            return ResponseEntity.ok(updateCustomer);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        try{
            customerService.deleteCustomer(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}