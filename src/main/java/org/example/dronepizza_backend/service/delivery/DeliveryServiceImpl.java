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
import java.time.LocalTime;
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
    public Delivery addDelivery(String address, int pizzaType, LocalTime expectedDeliveryTime) {
        Delivery delivery = new Delivery();
        delivery.setAddress(address);

        Pizza pizza = new Pizza();
        switch (pizzaType) {
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

        //if a delivery has already been finished, it will throw an error
        if(delivery.getActualDeliveryTime() != null) {
            throw new IllegalArgumentException("Delivery has already been delivered");
        }
        //if a delivery is already scheduled, it will throw an error
        if(delivery.getDrone() != null) {
            throw new IllegalArgumentException("Delivery has already been scheduled");
        }

        //utilises helper method to assign available drone
        delivery.setDrone(findAvailableDrone());

        return deliveryRepository.save(delivery);
    }

    //helper method to find available drone
    private Drone findAvailableDrone() {
        List<Delivery> allDeliveries = deliveryRepository.findAll();
        List<Drone> allDrones = droneRepository.findAll();

        for (Delivery delivery : allDeliveries) {
            if (delivery.getDrone() != null) {
                if (!allDrones.isEmpty()) {
                    allDrones.removeIf(
                            drone -> delivery.getDrone().getId() == drone.getId()
                    );
                }
            }
        }

        //if there are no available drones, a random one will be added back to the list
        //in essence, this creates a backlog for the drone, but the random assignment is
        //probably bad from a business perspective
        if(allDrones.isEmpty()){
            int numberOfDrones = droneRepository.findAll().size();
            allDrones.add(droneRepository.findById(
                    (int) (Math.random() * numberOfDrones)+1
            ).orElseThrow());
        }

        return allDrones.getFirst();
    }

    @Override
    public Delivery finishDelivery(int deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow();

        if(delivery.getDrone() == null) {
            throw new IllegalArgumentException("Delivery is not assigned a drone");
        }
        if (delivery.getActualDeliveryTime() != null) {
            throw new IllegalArgumentException("Delivery has already been finished");
        }

        delivery.setActualDeliveryTime(LocalTime.now());

        return deliveryRepository.save(delivery);
    }
}
