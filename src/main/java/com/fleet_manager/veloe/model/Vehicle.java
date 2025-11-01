package com.fleet_manager.veloe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Marca é obrigatório!")
    @Column(name = "Brand", nullable = false)
    private String brand;

    @NotBlank(message = "Modelo é obrigatório!")
    @Column(name = "Model", nullable = false)
    private String model;

    @NotBlank(message = "Placa é obrigatório!")
    @Column(name = "Plate", nullable = false, unique = true)
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tipo do Veiculo é obrigatório")
    private VehicleType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_owner")
    private Customer owner;

    public Vehicle() {}

    public Vehicle(String brand, String model, String licensePlate, VehicleType type, Customer owner){
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.licensePlate = licensePlate;
        this.owner = owner;
    }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }

    public Customer getOwner() { return owner; }
    public void setOwner(Customer owner) { this.owner = owner; }
}
