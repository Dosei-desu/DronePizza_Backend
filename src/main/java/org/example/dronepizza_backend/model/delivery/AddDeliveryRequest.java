package org.example.dronepizza_backend.model.delivery;


import java.sql.Timestamp;

public record AddDeliveryRequest(
        String address,
        int pizzaId,
        int minutesUntilExpectedArrival
        //this will be used to more easily parse delivery times dynamically, since passing time format
        //from frontend gives me a headache to deal with right now | TODO fix
) {
}
