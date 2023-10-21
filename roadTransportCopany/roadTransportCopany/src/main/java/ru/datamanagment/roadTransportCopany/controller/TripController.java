package ru.datamanagment.roadTransportCopany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.datamanagment.roadTransportCopany.exeption.ResourceNotFoundException;
import ru.datamanagment.roadTransportCopany.model.Payment;
import ru.datamanagment.roadTransportCopany.model.Trip;
import ru.datamanagment.roadTransportCopany.model.TripFilterForm;
import ru.datamanagment.roadTransportCopany.repo.CargoRepository;
import ru.datamanagment.roadTransportCopany.repo.DriverRepository;
import ru.datamanagment.roadTransportCopany.repo.LocationRepository;
import ru.datamanagment.roadTransportCopany.repo.VehicleRepository;
import ru.datamanagment.roadTransportCopany.service.CustomerService;
import ru.datamanagment.roadTransportCopany.service.PaymentService;
import ru.datamanagment.roadTransportCopany.service.TripService;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// TripController.java
@Controller
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private TripService tripService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private CargoRepository cargoRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private PaymentService paymentService;



    @GetMapping("/{id}")
    public String getTrip(@PathVariable("id") Long id, Model model) {
        Optional<Trip> trip = tripService.getTripById(id);
        model.addAttribute("trip", trip);
        return "trip";
    }

    @GetMapping
    public String getAllTrips(Model model, @ModelAttribute("filterForm") TripFilterForm filterForm) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        if (filterForm.check() == false){
            List<Trip> trips = tripService.getAllTrips();
            model.addAttribute("trips", trips);
        }
        else {
            List<Trip> trips = tripService.getTripsByFilter(filterForm);
            model.addAttribute("trips", trips);
        }


        model.addAttribute("trip", new Trip());
        model.addAttribute("filterForm", new TripFilterForm(0L,0L,0L, 0L,0L));
        model.addAttribute("drivers", driverRepository.findAll());
        model.addAttribute("locations", locationRepository.findAll());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("cargos", cargoRepository.findAll());
        model.addAttribute("vehicles", vehicleRepository.findAll());
        return "trip";
    }

    @PostMapping
    public String saveTrip(@ModelAttribute("trip") Trip trip) throws ParseException {
        trip.setEndDate(LocalDate.parse(trip.getStrdate()));
        Payment payment =new Payment(tripService.saveOrUpdateTrip(trip), trip.getPrice(), trip.getStartDate());
        paymentService.createPayment(payment);
        return "redirect:/trips";
    }

    @GetMapping("/{id}/edit")
    public String editTrip(@PathVariable("id") Long id, Model model) {
        Optional<Trip> trip = tripService.getTripById(id);
        model.addAttribute("trip", trip.get());
        return "trip-form";
    }

    @GetMapping("/{id}/delete")
    public String deleteTrip(@PathVariable("id") Long id) throws ResourceNotFoundException {
        tripService.deleteTrip(id);
        return "redirect:/trips";
    }

    public static boolean areAllFieldsEmpty(Object obj) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(obj);
            if (value != null && !value.equals(field.getType().getDeclaredConstructor().newInstance())) {
                return false;
            }
        }
        return true;
    }
}
