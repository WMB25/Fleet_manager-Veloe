package com.fleet_manager.veloe.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VehicleRequest {
    @NotBlank(message = "Modelo é obrigatório!")
    private String model;

    @NotBlank(message = "Marca é obrigatório!")
    private String brand;

    @NotBlank(message = "Placa é obrigatório!")
    private String licensePlate;

    @NotNull(message = "Tipo do veículo é obrigatório!")
    private VehicleType type;

    @NotNull(message = "ID do cliente é obrigatório!")
    private Long customerId;

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
}