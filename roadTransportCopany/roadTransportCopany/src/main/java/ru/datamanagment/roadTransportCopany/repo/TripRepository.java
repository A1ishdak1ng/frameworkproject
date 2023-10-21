package ru.datamanagment.roadTransportCopany.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.datamanagment.roadTransportCopany.model.Location;
import ru.datamanagment.roadTransportCopany.model.Trip;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long>, JpaSpecificationExecutor<Trip> {
    @Query(nativeQuery = true, value = "select * from trips where customer_id=?1")
    List<Trip> findTripsByCustomer(Long customerId);

    @Query(nativeQuery = true, value = "select * from trips where start_location=?1 and end_location=?2 and customer_id=?3")

    List<Trip> findTripsByStartLocationAndEndLocationAndCustomer(Long  startLocation, Long endLocation, Long id);
    @Query(nativeQuery = true, value = "select * from trips where start_location=?1 and customer_id=?2")

    List<Trip> findTripsByStartLocationAndCustomer(Long startLocation, Long id);
    List<Trip> findTripsByStartLocationAndEndLocationAndCustomerAndStartDate(Location startLocation, Location endLocation, Long id, LocalDate startDate);
    List<Trip> findTripsByStartLocationAndEndLocationAndStartDate(Location startLocation, Location endLocation, LocalDate startDate);
    @Query(nativeQuery = true, value = "select * from trips where start_location=?1 and end_location=?2")
    List<Trip> findTripsByStartLocationAndEndLocation(Long startLocation, Long endLocation);
    @Query(nativeQuery = true, value = "select * from trips where driver_id=?1")

    List<Trip> findTripsByDriver(Long driverId);
    @Query(nativeQuery = true, value = "select * from trips where vehicle_id=?1")
    List<Trip> findTripsByVehicle(Long vehicleId);
    @Query(nativeQuery = true, value = "select * from trips where start_location=?1")
    List<Trip> findTripsByStartLocation(Long startLocation);
    @Query(nativeQuery = true, value = "select * from trips where start_location=?1 and end_location=?2 and start_date=?3 and customer_id=?4")

    List<Trip> findTripsByStartLocationAndEndLocationAndStartDateAndCustomer(Location startLocation, Location endLocation, LocalDate startDate, Long id);
//    List<Trip> findTripsByStartLocation_IdAndEndLocation_IdAndCustomer_Id(Long  startLocation, Long endLocation, Long id, LocalDate startDate);


}