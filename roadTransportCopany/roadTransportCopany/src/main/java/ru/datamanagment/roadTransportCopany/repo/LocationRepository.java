package ru.datamanagment.roadTransportCopany.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.datamanagment.roadTransportCopany.model.Location;
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
