package com.fleet_manager.veloe.service;

import com.fleet_manager.veloe.model.Customer;
import com.fleet_manager.veloe.model.Vehicle;
import com.fleet_manager.veloe.model.VehicleRequest;
import com.fleet_manager.veloe.repository.CustomerRepository;
import com.fleet_manager.veloe.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
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
        String plate = request.getLicensePlate().toUpperCase();
        Long customerId = request.getCustomerId();

        if(vehicleRepository.existsByLicensePlate(plate)){
            throw new IllegalArgumentException("Já existe um veículo com a placa: " + plate);
        }
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com documento: " + request.getCustomerId()));

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(request.getBrand());
        vehicle.setModel(request.getModel());
        vehicle.setLicensePlate(plate);
        vehicle.setCustomer(customer);
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

    public List<Vehicle> findByCustomerId(@NotNull Long customerId) {
        return vehicleRepository.findByCustomerId(customerId);
    }

    public Vehicle updateVehicle(@NotNull Long id, @NotNull VehicleRequest request) {
        Vehicle existingVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado com ID: " + id));

        if(!existingVehicle.getLicensePlate().equals(request.getLicensePlate()) && vehicleRepository.existsByLicensePlate(request.getLicensePlate())){
            throw new IllegalArgumentException("Já existe um veículo cadastrado com placa: " + request.getLicensePlate());
        }

        Customer customer = existingVehicle.getCustomer();
        if(!existingVehicle.getCustomer().getId().equals(request.getCustomerId())){
            customer =  customerRepository.findById(request.getCustomerId()).orElseThrow(() -> new IllegalArgumentException("Clente não encontrado com ID: " + request.getCustomerId()));
        }

        existingVehicle.setBrand(request.getBrand());
        existingVehicle.setModel(request.getModel());
        existingVehicle.setType(request.getType());
        existingVehicle.setLicensePlate(request.getLicensePlate().toUpperCase());
        existingVehicle.setCustomer(customer);

        return vehicleRepository.save(existingVehicle);
    }

    public void deleteVehicle(@NotNull Long id) {
        if(!vehicleRepository.existsById(id)){
            throw new IllegalArgumentException("Veículo não encontrado com ID: " + id);
        }

        vehicleRepository.deleteById(id);
    }

    public Long countVehicleByCustomer(@NotNull Long customerId){
        return vehicleRepository.countByCustomerId(customerId);
    }
}