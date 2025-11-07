package com.fleet_manager.veloe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Nome é obrigatório!")
    private String name;

    @NotNull(message = "Documento é obrigatório!")
    @Column(name = "document", nullable = false)
    private String document;

    @Email(message = "Adicione um email válido!")
    private String email;
    private String phone;

    public Customer() {}

    public Customer(String name, String document, String email, String phone){
        this.name = name;
        this.document = document;
        this.email = email;
        this.phone = phone;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDocument() { return document; }
    public void setDocument(String document) { this.document = document; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}