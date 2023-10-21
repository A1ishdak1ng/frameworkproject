package ru.datamanagment.roadTransportCopany.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.datamanagment.roadTransportCopany.model.Cargo;
import ru.datamanagment.roadTransportCopany.model.Customer;
import ru.datamanagment.roadTransportCopany.model.Payment;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
}