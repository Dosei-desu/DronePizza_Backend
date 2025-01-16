package org.example.dronepizza_backend.service.delivery;

import lombok.RequiredArgsConstructor;
import org.example.dronepizza_backend.model.delivery.Delivery;
import org.example.dronepizza_backend.model.delivery.Pizza;
import org.example.dronepizza_backend.repository.delivery.DeliveryRepository;
import org.example.dronepizza_backend.repository.delivery.PizzaRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final PizzaRepository pizzaRepository;


    @Override
    public Pizza addPizza(double price, String title){
        Pizza pizza = new Pizza();
        pizza.setPrice(price);
        pizza.setTitle(title);
        return pizzaRepository.save(pizza);
    }

    @Override
    public List<Delivery> getDeliveries() {
        //calls native query in repository that selects all where actual delivery time is null
        return deliveryRepository.findAllUndelivered();
    }

    @Override
    public Delivery addDelivery(String address, int pizzaId, LocalDateTime expectedDeliveryTime) {
        Delivery delivery = new Delivery();
        delivery.setAddress(address);

        Pizza pizza = pizzaRepository.findById(pizzaId).orElseThrow();
        delivery.setPizza(pizza);

        delivery.setExpectedDeliveryTime(expectedDeliveryTime);

        return deliveryRepository.save(delivery);
    }

    @Override
    public List<Delivery> getDeliveryQueue() {
        return List.of();
    }

    @Override
    public Delivery scheduleDelivery(int deliveryId) {
        return null;
    }

    @Override
    public Delivery finishDelivery(int deliveryId) {
        return null;
    }
}
