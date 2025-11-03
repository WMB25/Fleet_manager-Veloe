package com.fleet_manager.veloe.repository;

import com.fleet_manager.veloe.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByLicensePlate(String licensePlate);

    List<Vehicle> findByOwnerID(Long ownerID);

    boolean existByLicensePlate(String licensePlate);

    @Query("SELECT v FROM Vehicle v WHERE v.customer.id = :customerId")
    List<Vehicle> findVehiclesByCustomer(@Param("customerId") Long customerId);

    @Query("SELECT COUNT(v) FROM Vehicle v WHERE v.customer.id = :customerId")
    Long countByCustomerId(@Param("customerId") Long customerId);
}