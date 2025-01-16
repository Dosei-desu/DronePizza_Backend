package org.example.dronepizza_backend.service.delivery;


import org.example.dronepizza_backend.model.delivery.Delivery;
import org.example.dronepizza_backend.model.delivery.Pizza;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface DeliveryService {

    Pizza addPizza(double price, String title);

    List<Delivery> getDeliveries(); //all deliveries not delivered

    Delivery addDelivery(String address, int pizzaId, LocalDateTime expectedDeliveryTime);
    //i figure that an expected delivery time is added as soon as a delivery is added
    //since customers will usually get something like "arrival time 30 minutes"

    List<Delivery> getDeliveryQueue(); //all deliveries without drone

    Delivery scheduleDelivery(int deliveryId); //this is where droneId will be added

    Delivery finishDelivery(int deliveryId); //this is where actual delivery time is added
}
