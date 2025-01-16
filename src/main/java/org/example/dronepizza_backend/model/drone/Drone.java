package org.example.dronepizza_backend.model.drone;

import jakarta.persistence.*;
import lombok.*;
import org.example.dronepizza_backend.model.delivery.Delivery;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String UUID;
    private DroneStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "station_id")
    private Station station;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "drone_id")
    private List<Delivery> deliveries;
}
