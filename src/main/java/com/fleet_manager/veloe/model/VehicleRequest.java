package com.fleet_manager.veloe.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VehicleRequest {
    @NotBlank(message = "Modelo é obrigatorio!")
    private String model;

    @NotBlank(message = "Maeca é obrigatorio!")
    private String brand;

    @NotBlank(message = "Placa é obrigatorio!")
    private String licensePlate;

    @NotNull(message = "Tipo do veiculo é obrigatoeio!")
    private VehicleType type;

    @NotNull(message = "ID do cliente é obrigatoeio!")
    private Long ownerId;

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }

    public Long getOwnerID() { return ownerId; }
    public void setOwnerID(Long ownerID) { this.ownerId = ownerID; }
}