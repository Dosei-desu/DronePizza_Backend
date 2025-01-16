package org.example.dronepizza_backend.controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.dronepizza_backend.service.delivery.DeliveryService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;
}
