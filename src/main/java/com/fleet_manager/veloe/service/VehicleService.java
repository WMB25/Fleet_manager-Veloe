package com.fleet_manager.veloe.service;

import com.fleet_manager.veloe.model.Customer;
import com.fleet_manager.veloe.model.Vehicle;
import com.fleet_manager.veloe.model.VehicleRequest;
import com.fleet_manager.veloe.repository.CustomerRepository;
import com.fleet_manager.veloe.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Vehicle createVehicle(VehicleRequest request){
        if(vehicleRepository.existsByLicensePlate(request.getLicensePlate())){
            throw new IllegalArgumentException("Já existe um veiculo com esta placa: " + request.getLicensePlate());
        }
        Customer owner = customerRepository.findById(request.getOwnerID())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com este documento: " + request.getOwnerID()));

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(request.getBrand());
        vehicle.setModel(request.getModel());
        vehicle.setLicensePlate(request.getLicensePlate());
        vehicle.setOwner(owner);
        vehicle.setType(request.getType());

        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }
    
    public Optional<Vehicle> findById(@NotNull Long id) {
        return vehicleRepository.findById(id);
    }
    
    public Optional<Vehicle> findByLicensePlate(@NotNull String plate) {
        return vehicleRepository.findByLicensePlate(plate.toUpperCase());
    }

    public List<Vehicle> findByOwnerId(@NotNull Long ownerId) {
        return vehicleRepository.findByOwnerId(ownerId);
    }

    public Vehicle updateVehicle(@NotNull Long id, @NotNull VehicleRequest request) {
        Vehicle existingVehicle = vehicleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Veiculo não encontrado com esta ID: " + id));

        if(!existingVehicle.getLicensePlate().equals(request.getLicensePlate()) && vehicleRepository.existsByLicensePlate(request.getLicensePlate())){
            throw new IllegalArgumentException("Já existe um veiculo cadastrado com esta placa: " + request.getLicensePlate());
        }

        Customer owner = existingVehicle.getOwner();
        if(!existingVehicle.getOwner().getId().equals(request.getOwnerID())){
            owner =  customerRepository.findById(request.getOwnerID()).orElseThrow(() -> new IllegalArgumentException("Clente não encontrado com ID: " + request.getOwnerID()));
        }

        existingVehicle.setBrand(request.getBrand());
        existingVehicle.setModel(request.getModel());
        existingVehicle.setType(request.getType());
        existingVehicle.setLicensePlate(request.getLicensePlate().toUpperCase());
        existingVehicle.setOwner(owner);

        return vehicleRepository.save(existingVehicle);
    }

    public void deleteVehicle(@NotNull Long id) {
        if(!vehicleRepository.existsById(id)){
            throw new IllegalArgumentException("Veiculo não encontrado com ID: " + id);
        }

        vehicleRepository.deleteById(id);
    }

    public Long countVehicleByOwner(@NotNull Long ownerId){
        return vehicleRepository.countByCustomerId(ownerId);
    }
}