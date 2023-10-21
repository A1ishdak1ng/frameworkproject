package ru.datamanagment.roadTransportCopany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.datamanagment.roadTransportCopany.exeption.ResourceNotFoundException;
import ru.datamanagment.roadTransportCopany.model.Location;
import ru.datamanagment.roadTransportCopany.model.Trip;
import ru.datamanagment.roadTransportCopany.model.TripFilterForm;
import ru.datamanagment.roadTransportCopany.repo.TripRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {
    private final TripRepository tripRepository;

    @Autowired
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public List<Trip> getAllTrips() {
            return tripRepository.findAll();
    }


    public Optional<Trip> getTripById(Long id) {
        return tripRepository.findById(id);
    }

    public Trip createTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    public Trip updateTrip(Long id, Trip tripDetails) throws ResourceNotFoundException {
        Optional<Trip> optionalTrip = tripRepository.findById(id);
        if (optionalTrip.isPresent()) {
            Trip trip = optionalTrip.get();
            trip.setCustomer(tripDetails.getCustomer());
            trip.setDriver(tripDetails.getDriver());
            trip.setVehicle(tripDetails.getVehicle());
            trip.setDistance(tripDetails.getDistance());
            trip.setStartDate(tripDetails.getStartDate());
            trip.setEndDate(tripDetails.getEndDate());
            trip.setStartLocation(tripDetails.getStartLocation());
            trip.setEndLocation(tripDetails.getEndLocation());
            return tripRepository.save(trip);
        } else {
            throw new ResourceNotFoundException("Trip not found with id: " + id);
        }
    }

    public void deleteTrip(Long id) throws ResourceNotFoundException {
        Optional<Trip> optionalTrip = tripRepository.findById(id);
        if (optionalTrip.isPresent()) {
            tripRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Trip not found with id: " + id);
        }
    }

    public Trip saveOrUpdateTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    public List<Trip> getTripsByCustomerId(Long id) {
        System.out.println("CUSTOMER ID "  + id);
        return tripRepository.findTripsByCustomer(id);
    }

    public List<Trip> searchTrips(Location startLocation, Location endLocation, LocalDate startDate, Long id) {
        if (startDate != null && startDate != null && endLocation !=null){
            return tripRepository.findTripsByStartLocationAndEndLocationAndStartDateAndCustomer(startLocation, endLocation, startDate, id);
        } else if (startLocation != null && endLocation != null) {
            return tripRepository.findTripsByStartLocationAndEndLocationAndCustomer(startLocation.getId(), endLocation.getId(), id);
        } else if (startLocation != null){
            return tripRepository.findTripsByStartLocationAndCustomer(startLocation.getId(), id);
        }else {
            return null;
        }
        /*if (startDate.equals(null) ) {
            return tripRepository.findTripsByStartLocationAndEndLocationAndCustomer(startLocation, endLocation, id);
        }else if (endLocation == 0 && startDate == null){
            return tripRepository.findTripsByStartLocationAndCustomer(startLocation, id);
        }else {*/
//            return tripRepository.findTripsByStartLocation_IdAndEndLocation_IdAndCustomer_Id(startLocation, endLocation, id, startDate);


    }

    public List<Trip> getTripsByFilter(TripFilterForm filterForm) {
        if (filterForm.getStartLocation() != 0 && filterForm.getEndLocation() !=0){
            return tripRepository.findTripsByStartLocationAndEndLocation(filterForm.getStartLocation(), filterForm.getEndLocation());
        } else if (filterForm.getCustomerId() != 0) {

            return tripRepository.findTripsByCustomer(filterForm.getCustomerId());
        }else if (filterForm.getDriverId() !=0){
            return tripRepository.findTripsByDriver(filterForm.getDriverId());
        }else if (filterForm.getVehicleId() !=0){
            return tripRepository.findTripsByVehicle(filterForm.getVehicleId());
        } else if (filterForm.getStartLocation() != 0) {
            return tripRepository.findTripsByStartLocation(filterForm.getStartLocation());
        }else {
            return tripRepository.findAll();
        }
    }
}