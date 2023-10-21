package ru.datamanagment.roadTransportCopany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.datamanagment.roadTransportCopany.model.Customer;
import ru.datamanagment.roadTransportCopany.model.Location;
import ru.datamanagment.roadTransportCopany.model.Payment;
import ru.datamanagment.roadTransportCopany.model.Trip;
import ru.datamanagment.roadTransportCopany.repo.CargoRepository;
import ru.datamanagment.roadTransportCopany.repo.DriverRepository;
import ru.datamanagment.roadTransportCopany.repo.LocationRepository;
import ru.datamanagment.roadTransportCopany.repo.VehicleRepository;
import ru.datamanagment.roadTransportCopany.service.CustomerService;
import ru.datamanagment.roadTransportCopany.service.PaymentService;
import ru.datamanagment.roadTransportCopany.service.TripService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {
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

    @GetMapping("/")
    public String redirectToHome(Model model){

        return "redirect:/home";
    }
    @GetMapping("/home")
    public String home(Model model, @ModelAttribute("search") Trip trip,
                       @AuthenticationPrincipal Customer customer){
        System.out.println(trip.getStartDate() + "-----------------");
        List<Trip> trips = tripService.searchTrips(trip.getStartLocation(), trip.getEndLocation(), trip.getStartDate(), customer.getId() );
        model.addAttribute("trips", trips);
        model.addAttribute("trip", new Trip());
        model.addAttribute("drivers", driverRepository.findAll());
        model.addAttribute("locations", locationRepository.findAll());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("cargos", cargoRepository.findAll());
        model.addAttribute("vehicles", vehicleRepository.findAll());
        model.addAttribute("search", new Trip());
        return "home";
    }

    @PostMapping("/home/newtrip")
    public String saveTrip(@ModelAttribute("trip") Trip trip, @AuthenticationPrincipal Customer customer) {
        trip.setCustomer(customer);
        Payment payment =new Payment(tripService.saveOrUpdateTrip(trip), trip.getDistance()*trip.getCargo().getPrice(), trip.getStartDate());
        paymentService.createPayment(payment);
        return "redirect:/home";
    }

    /*@PostMapping("/search")
    public String search(Model model, @ModelAttribute("search") Trip trip,
                         @AuthenticationPrincipal Customer customer) {
        List<Trip> trips = tripService.searchTrips(trip.getStartLocation(), trip.getEndLocation(), trip.getStartDate(), customer.getId() );
        model.addAttribute("trips", trips);
        model.addAttribute("trip", new Trip());
        model.addAttribute("drivers", driverRepository.findAll());
        model.addAttribute("locations", locationRepository.findAll());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("cargos", cargoRepository.findAll());
        model.addAttribute("vehicles", vehicleRepository.findAll());
        model.addAttribute("search", new Trip());
        return "home";
    }*/


    @GetMapping("/wlkfweklfwke/fwfewkfmnlkwmwkefmw")
    public String get(){
        paymentService.get(4L);
        return "home";
    }
}
