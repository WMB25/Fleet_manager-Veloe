package com.fleet_manager.veloe.controller;

import com.fleet_manager.veloe.model.Vehicle;
import com.fleet_manager.veloe.model.VehicleRequest;
import com.fleet_manager.veloe.model.VehicleType;
import com.fleet_manager.veloe.service.VehicleService;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateVehicle() throws Exception {
        VehicleRequest request = new VehicleRequest();
        request.setBrand("Toyota");
        request.setModel("Corolla");
        request.setLicensePlate("ABC1234");
        request.setType(VehicleType.CAR);
        request.setCustomeID(1L);

        Vehicle response = new Vehicle();
        response.setId(1L);
        response.setBrand("Toyota");
        response.setLicensePlate("ABC1234");

        when(vehicleService.createVehicle(any(VehicleRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.brand").value("Toyota"));
    }

    @Test
    void shouldGetAllVehicles() throws Exception {
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1L);
        vehicle1.setBrand("Toyota");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(2L);
        vehicle2.setBrand("Honda");

        when(vehicleService.findAll()).thenReturn(Arrays.asList(vehicle1, vehicle2));

        mockMvc.perform(get("/api/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].brand").value("Toyota"))
                .andExpect(jsonPath("$[1].brand").value("Honda"));
    }

    @Test
    void shouldGetVehicleById() throws Exception {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setBrand("Toyota");

        when(vehicleService.findById(1L)).thenReturn(Optional.of(vehicle));

        mockMvc.perform(get("/api/vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.brand").value("Toyota"));
    }

    @Test
    void shouldReturnNotFoundWhenVehicleNotExists() throws Exception {
        when(vehicleService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/vehicles/1"))
                .andExpect(status().isNotFound());
    }
}