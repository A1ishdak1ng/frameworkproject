package ru.datamanagment.roadTransportCopany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.datamanagment.roadTransportCopany.exeption.ResourceNotFoundException;
import ru.datamanagment.roadTransportCopany.model.Payment;
import ru.datamanagment.roadTransportCopany.service.PaymentService;

import java.util.List;

@Controller
public class PaymentsController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payments")
    public String showAllPayments(Model model) {
        List<Payment> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        model.addAttribute("newPayment", new Payment());
        model.addAttribute("nullPayment", paymentService.getNullPayment());
        return "payments";
    }

    @PostMapping("/payments/new")
    public String savePayment(@ModelAttribute("newPayment") Payment payment) {
        paymentService.createPayment(payment);
        return "redirect:/payments";
    }

    @GetMapping("/payments/edit/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) throws ResourceNotFoundException {
        Payment payment = paymentService.getPaymentById(id);
        model.addAttribute("payment", payment);
        return "payment-form";
    }

    @GetMapping("/payments/delete/{id}")
    public String deletePayment(@PathVariable("id") long id) throws ResourceNotFoundException {
        paymentService.deletePayment(id);
        return "redirect:/payments";
    }
}
