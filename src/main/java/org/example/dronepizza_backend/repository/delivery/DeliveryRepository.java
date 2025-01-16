package org.example.dronepizza_backend.repository.delivery;

import org.example.dronepizza_backend.model.delivery.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {

    @Modifying
    @Query(
        value = "SELECT * FROM dronepizza.delivery WHERE actual_delivery_time IS NULL",
        nativeQuery = true)
    List<Delivery> findAllUndelivered();

    //this one is really long, so might do native query instead | TODO make native query
    List<Delivery> findByDroneIsNullAndActualDeliveryTimeIsNull();
}
