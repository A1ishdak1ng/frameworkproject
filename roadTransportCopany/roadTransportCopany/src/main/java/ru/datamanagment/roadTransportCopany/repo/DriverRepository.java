package ru.datamanagment.roadTransportCopany.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.datamanagment.roadTransportCopany.model.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}
