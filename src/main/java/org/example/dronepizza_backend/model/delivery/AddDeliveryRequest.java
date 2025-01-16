package org.example.dronepizza_backend.model.delivery;


import java.sql.Timestamp;

public record AddDeliveryRequest(
        String address,
        int pizzaType
) {
}
