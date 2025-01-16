package org.example.dronepizza_backend.service.delivery;

import lombok.RequiredArgsConstructor;
import org.example.dronepizza_backend.model.delivery.Delivery;
import org.example.dronepizza_backend.model.delivery.Pizza;
import org.example.dronepizza_backend.model.drone.Drone;
import org.example.dronepizza_backend.repository.delivery.DeliveryRepository;
import org.example.dronepizza_backend.repository.delivery.PizzaRepository;
import org.example.dronepizza_backend.repository.drone.DroneRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final PizzaRepository pizzaRepository;
    private final DroneRepository droneRepository;


    @Override
    public Pizza addPizza(double price, String title) {
        Pizza pizza = new Pizza();
        pizza.setPrice(price);
        pizza.setTitle(title);
        return pizzaRepository.save(pizza);
    }

    @Override
    public List<Delivery> getDeliveries() {
        //calls native query in repository that selects all where actual delivery time is null
        //might not be necessary, but couldnt find the right JPA Repository string of words
        return deliveryRepository.findAllUndelivered();
    }

    @Override
    public Delivery addDelivery(String address, int pizzaType, LocalDateTime expectedDeliveryTime) {
        Delivery delivery = new Delivery();
        delivery.setAddress(address);

        Pizza pizza = new Pizza();
        switch(pizzaType){
            case 1:
                pizza.setPrice(59.00);
                pizza.setTitle("Margherita");
                break;
            case 2:
                pizza.setPrice(65.00);
                pizza.setTitle("Pepperoni");
                break;
            case 3:
                pizza.setPrice(68.50);
                pizza.setTitle("Pineapple");
                break;
            default:
                throw new IllegalArgumentException("Invalid pizza type");
        }

        pizza.setDelivery(delivery);
        pizzaRepository.save(pizza);

        delivery.setPizza(pizza);


        delivery.setExpectedDeliveryTime(expectedDeliveryTime);

        return deliveryRepository.save(delivery);
    }

    @Override
    public List<Delivery> getDeliveryQueue() {
        return deliveryRepository.findByDroneIsNullAndActualDeliveryTimeIsNull();
    }

    @Override
    public Delivery scheduleDelivery(int deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow();
        delivery.setDrone(findAvailableDrone());
        return deliveryRepository.save(delivery);
    }

    private Drone findAvailableDrone() {
        List<Delivery> allDeliveries = deliveryRepository.findAll();
        List<Drone> allDrones = droneRepository.findAll();

        System.out.println("list before removal pass:" + allDrones.size());
        for (Delivery delivery : allDeliveries) {
            if (delivery.getDrone() != null) {
                allDrones.removeIf(
                        drone -> delivery.getDrone().getId() == drone.getId()
                );
            }
        }
        System.out.println("list after removal pass:" + allDrones.size());

        return allDrones.getFirst();
    }

    @Override
    public Delivery finishDelivery(int deliveryId) {
        return null;
    }
}
