package org.example.dronepizza_backend.model.drone;

import jakarta.persistence.*;
import lombok.*;
import org.example.dronepizza_backend.model.delivery.Delivery;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String UUID;
    private DroneStatus status;
    private int stationId;

    @OneToMany(mappedBy = "drone",cascade = CascadeType.ALL)
    @JoinColumn(name = "drone_id")
    private List<Delivery> deliveries;
}
