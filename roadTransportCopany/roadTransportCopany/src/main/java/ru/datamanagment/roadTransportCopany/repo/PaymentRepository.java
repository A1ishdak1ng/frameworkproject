package ru.datamanagment.roadTransportCopany.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.datamanagment.roadTransportCopany.model.Payment;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(nativeQuery = true, value = "select p.id,p.amount, p.created_at_date,p.trip_id\n" +
            "from payments p join trips t on p.trip_id = t.id\n" +
            "join customers c on c.id = t.customer_id\n" +
            "where c.id=?1 order by trip_id desc limit 3")
    List<Payment> findFirst3ByCustomer(Long id);

    @Query(nativeQuery = true, value = "select t.id as trip_id from payments p right join trips t on p.trip_id=t.id where p.amount is null")
    List<Long> findTripsWithNoVal();
    Optional<Payment> findPaymentByTrip(Long id);

    List<Payment> findPaymentsByTrip_Customer(Long customer_id);

    /*@Procedure("payment")
    void calculatePriceForTrip(Long id);
*/
    @Query(value = "CALL payment(CAST(:id as bigint)", nativeQuery = true)
    void calculatePriceForTrip(@Param("id") Long id);
}
