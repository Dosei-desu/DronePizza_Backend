package org.example.dronepizza_backend.model.delivery;

import jakarta.persistence.*;
import lombok.*;
import org.example.dronepizza_backend.model.drone.Drone;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String address;
    private Timestamp expectedDeliveryTime;
    private Timestamp actualDeliveryTime;

    @ManyToOne
    @JoinColumn(name = "drone_id")
    private Drone drone;
    //keeps giving a dumb error (which doesnt change anything) about Drone
    //being the wrong 'type'

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Pizza pizza;
}
