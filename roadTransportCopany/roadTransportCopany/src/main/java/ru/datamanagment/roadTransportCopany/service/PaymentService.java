package ru.datamanagment.roadTransportCopany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.datamanagment.roadTransportCopany.exeption.ResourceNotFoundException;
import ru.datamanagment.roadTransportCopany.model.Payment;
import ru.datamanagment.roadTransportCopany.repo.PaymentRepository;
import ru.datamanagment.roadTransportCopany.repo.TripRepository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private TripRepository tripRepository;
    @Transactional
    public Payment createPayment(Payment payment) {
        payment.setCreatedAt(payment.getTrip().getStartDate());
        return paymentRepository.save(payment);

    }

    public void get(Long id){
        paymentRepository.calculatePriceForTrip(id);
    }

    public Payment getPaymentById(Long id) throws ResourceNotFoundException {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment id " +  id));
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment updatePayment(Long id, Payment paymentDetails) throws ResourceNotFoundException {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment id " + id));
        payment.setAmount(paymentDetails.getAmount());
        payment.setCreatedAt(paymentDetails.getCreatedAt());
        payment.setTrip(paymentDetails.getTrip());
        Payment updatedPayment = paymentRepository.save(payment);
        return updatedPayment;
    }

    public void deletePayment(Long id) throws ResourceNotFoundException {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment id" + id));
        paymentRepository.delete(payment);
    }

    public List<Payment> findFirst3ByCustomerId(Long cus_id){
        return paymentRepository.findFirst3ByCustomer(cus_id);
    }

    public List<Payment> findFirst3ByTrip_Customer(Long customer_id){
        return paymentRepository.findPaymentsByTrip_Customer(customer_id.longValue());
    }

    public List<Long> getNullPayment() {
        return paymentRepository.findTripsWithNoVal();
    }
}
