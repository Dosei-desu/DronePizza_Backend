package org.example.dronepizza_backend.model.drone;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private double latitude;
    private double longitude;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "station_id")
    private List<Drone> drones;
    //keeps giving a dumb error (which doesnt change anything) about Drone
    //being the wrong 'type'
}
