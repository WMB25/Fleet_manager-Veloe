package com.fleet_manager.veloe.controller;

import com.fleet_manager.veloe.model.Vehicle;
import com.fleet_manager.veloe.model.VehicleRequest;
import com.fleet_manager.veloe.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<?> createVehicle(@Valid @RequestBody VehicleRequest request){
        try{
            Vehicle vehicle = vehicleService.createVehicle(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(vehicle);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Vehicle> getAllVehicle(){
        return vehicleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id){
        Optional<Vehicle> vehicle = vehicleService.findById(id);
        return vehicle.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/placa/{licensePlate}")
    public ResponseEntity<Vehicle> getVehicleByLicensePlate(@PathVariable String licensePlate){
        Optional<Vehicle> vehicle = vehicleService.findByLicensePlate(licensePlate);
        return vehicle.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{customerId}")
    public List<Vehicle> getVehicleByCustomer(@PathVariable Long customerId){
        return vehicleService.findByCustomerId(customerId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehicle(@PathVariable Long id, @Valid @RequestBody VehicleRequest request){
        try{
            Vehicle vehicle = vehicleService.updateVehicle(id, request);
            return ResponseEntity.ok(vehicle);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id){
        try {
            vehicleService.deleteVehicle(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/cliente/{customerId}/count")
    public ResponseEntity<Long> countVehicleByCustomer(@PathVariable Long customerId){
        Long count = vehicleService.countVehicleByCustomer(customerId);
        return ResponseEntity.ok(count);
    }
}