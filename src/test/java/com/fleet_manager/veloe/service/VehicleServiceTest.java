package com.fleet_manager.veloe.service;

import com.fleet_manager.veloe.model.Customer;
import com.fleet_manager.veloe.model.Vehicle;
import com.fleet_manager.veloe.model.VehicleRequest;
import com.fleet_manager.veloe.model.VehicleType;
import com.fleet_manager.veloe.repository.CustomerRepository;
import com.fleet_manager.veloe.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    void shouldCreateVehicleSuccesfully(){
        // Configuração do Request e das Entidades
        VehicleRequest request = new VehicleRequest();
        request.setBrand("Toyota");
        request.setModel("Corolla");
        request.setLicensePlate("ABC1234");
        request.setType(VehicleType.CAR);
        request.setOwnerID(1L);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("João Silva");

        Vehicle expectedVehicle = new Vehicle();
        expectedVehicle.setOwner(customer);
        expectedVehicle.setLicensePlate("ABC1234");
        expectedVehicle.setBrand("Toyota");
        expectedVehicle.setModel("Corolla");
        expectedVehicle.setType(VehicleType.CAR);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(vehicleRepository.existsByLicensePlate("ABC1234")).thenReturn(false);
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(expectedVehicle);

        Vehicle result = vehicleService.createVehicle(request);

        assertNotNull(result);
        assertEquals("ABC1234", result.getLicensePlate());
        assertEquals(customer.getName(), result.getOwner().getName());

        verify(vehicleRepository, times(1)).existsByLicensePlate("ABC1234");
        verify(customerRepository, times(1)).findById(1L);
        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }

    @Test
    void shouldThrowExceptionWhenLicensePlateExists(){
        VehicleRequest request = new VehicleRequest();
        request.setLicensePlate("ABC1234");
        request.setOwnerID(1L);

        when(vehicleRepository.existsByLicensePlate("ABC1234")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> vehicleService.createVehicle(request));

        assertEquals("Já existe um veiculo com esta placa: ABC1234", exception.getMessage());

        verify(vehicleRepository, times(1)).existsByLicensePlate("ABC1234");
        verify(customerRepository, never()).findById(anyLong());
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    @Test
    void shouldFindAllVehicles() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        Vehicle vehicleTwo = new Vehicle();
        vehicleTwo.setId(2L);

        List<Vehicle> expectedVehicles = Arrays.asList(vehicle, vehicleTwo);
        when(vehicleRepository.findAll()).thenReturn(expectedVehicles);

        List<Vehicle> result = vehicleService.findAll();

        assertEquals(2, result.size());
        verify(vehicleRepository, times(1)).findAll();
    }

    @Test
    void shouldFindVehicleById(){
        Vehicle expectedVehicle = new Vehicle();
        expectedVehicle.setId(1L);
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(expectedVehicle));

        Optional<Vehicle> result = vehicleService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(vehicleRepository, times(1)).findById(1L);
    }

    // Teste Adicional para Cobrir a Exceção de Cliente Não Encontrado
    @Test
    void shouldThrowExceptionWhenOwnerNotFound(){
        VehicleRequest request = new VehicleRequest();
        request.setLicensePlate("XYZ9876");
        request.setOwnerID(99L); // ID que não existe

        when(vehicleRepository.existsByLicensePlate(anyString())).thenReturn(false);
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> vehicleService.createVehicle(request));

        assertEquals("Cliente não encontrado com este documento: 99", exception.getMessage());

        verify(vehicleRepository, times(1)).existsByLicensePlate("XYZ9876");
        verify(customerRepository, times(1)).findById(99L);
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }
}