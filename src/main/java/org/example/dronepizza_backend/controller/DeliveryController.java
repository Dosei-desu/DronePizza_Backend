package org.example.dronepizza_backend.controller;

import org.example.dronepizza_backend.model.delivery.AddDeliveryRequest;
import org.example.dronepizza_backend.model.delivery.Delivery;
import org.example.dronepizza_backend.service.delivery.DeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;

        deliveryService.addDelivery("Testville 27",1,LocalTime.now().plusMinutes(30));
        deliveryService.addDelivery("Test Town 58",2,LocalTime.now().plusMinutes(27));
        deliveryService.addDelivery("Testland 2",3,LocalTime.now().plusMinutes(14));
    }

    @GetMapping("")
    public ResponseEntity<List<Delivery>> getDeliveries() {
        List<Delivery> deliveries = deliveryService.getDeliveries();

        return ResponseEntity.ok(deliveries);
    }

    @PostMapping("/add")
    public ResponseEntity<Delivery> addDelivery(@RequestBody AddDeliveryRequest request) {
        Delivery delivery = deliveryService.addDelivery(
                request.address(),
                request.pizzaType(),
                LocalTime.now().plusMinutes(30)
                //adds 30 minutes to time of creation
        );
        return ResponseEntity.ok(delivery);
    }

    @GetMapping("/queue")
    public ResponseEntity<List<Delivery>> getDeliveryQueue() {
        List<Delivery> deliveryQueue = deliveryService.getDeliveryQueue();
        return ResponseEntity.ok(deliveryQueue);
    }

    @GetMapping("/{id}/schedule")
    public ResponseEntity<Delivery> scheduleDelivery(@PathVariable int id) {
        Delivery scheduleDelivery = deliveryService.scheduleDelivery(id);
        return ResponseEntity.ok(scheduleDelivery);
    }

    @GetMapping("/{id}/finish")
    public ResponseEntity<Delivery> finishDelivery(@PathVariable int id) {
        Delivery finishedDelivery = deliveryService.finishDelivery(id);
        return ResponseEntity.ok(finishedDelivery);
    }

}
