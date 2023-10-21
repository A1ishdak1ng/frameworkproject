package ru.datamanagment.roadTransportCopany.model;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TripFilterForm {
    private Long startLocation;
    private Long endLocation;
    private Long customerId;
    private Long driverId;
    private Long vehicleId;
    private List<Long> arguments = new ArrayList<>();

    public TripFilterForm(Long startLocation, Long endLocation, Long customerId, Long driverId, Long vehicleId) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.customerId = customerId;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
    }

    public TripFilterForm() {
    }

    public boolean check() {
        ArrayList<Long> isEmpty = new ArrayList<>();
        isEmpty.clear();
        arguments.clear();
        if (startLocation == null){
            startLocation=0L;
        }
        if (startLocation != null || startLocation >0L){
            isEmpty.add(startLocation);

        }
        if (endLocation == null){
            endLocation=0L;
        }
        if (endLocation>0L){
            isEmpty.add(endLocation);

        }

        if (customerId == null){
            customerId=0L;
        }
        if (customerId>0){
            isEmpty.add(customerId);

        }

        if (driverId == null){
            driverId=0L;
        }

        if (driverId>0){
            isEmpty.add(driverId);

        }

        if (vehicleId == null){
            vehicleId=0L;
        }
        if (vehicleId>0){
            isEmpty.add(vehicleId);

        }

        if (isEmpty.size() == 0){
            return false;

        }else {
            this.arguments=isEmpty;
            return true;
        }


    }

/*
    @Override
    public Predicate toPredicate(Root<Trip> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (customerId != 0) {
            predicates.add( criteriaBuilder.equal(root.get("customer").get("id"), customerId));
        }
        if (driverId != null || driverId != 0) {
            predicates.add(criteriaBuilder.equal(root.get("driver").get("id"), driverId));
        }
        if (vehicleId != null || vehicleId != 0) {
            predicates.add( criteriaBuilder.equal(root.get("vehicle").get("id"), vehicleId));
        }
        if (startLocation != null || startLocation != 0) {
            predicates.add( criteriaBuilder.equal(root.get("startLocation").get("id"), startLocation));
        }
        if (endLocation != null || endLocation != 0) {
            predicates.add(criteriaBuilder.equal(root.get("endLocation").get("id"), endLocation));
        }
        this.predicates=predicates;
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
*/



}
