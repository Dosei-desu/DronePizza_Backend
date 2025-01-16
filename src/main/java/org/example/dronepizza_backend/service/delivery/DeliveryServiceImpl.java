package org.example.dronepizza_backend.service.delivery;

import lombok.RequiredArgsConstructor;
import org.example.dronepizza_backend.repository.delivery.DeliveryRepository;
import org.example.dronepizza_backend.repository.delivery.PizzaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final PizzaRepository pizzaRepository;


}
