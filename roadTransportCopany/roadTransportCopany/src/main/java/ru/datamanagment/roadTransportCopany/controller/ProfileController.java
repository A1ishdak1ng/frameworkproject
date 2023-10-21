package ru.datamanagment.roadTransportCopany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.datamanagment.roadTransportCopany.model.Customer;
import ru.datamanagment.roadTransportCopany.model.Payment;
import ru.datamanagment.roadTransportCopany.service.CustomerService;
import ru.datamanagment.roadTransportCopany.service.PaymentService;
import ru.datamanagment.roadTransportCopany.service.TripService;

import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private TripService tripService;

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal Customer customer){
        model.addAttribute("customer", customer);
//        model.addAttribute("payments", paymentService.findFirst3ByCustomerId(customer.getId()));
        model.addAttribute("payments", paymentService.findFirst3ByCustomerId(customer.getId()));
//        model.addAttribute("")
        //TODO: Нужно вытащить лист трипов
        model.addAttribute("trips", tripService.getTripsByCustomerId(customer.getId()));
        return "profile";
    }



}
