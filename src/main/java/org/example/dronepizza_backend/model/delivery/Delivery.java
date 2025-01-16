package org.example.dronepizza_backend.model.delivery;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "drone_id")
    private Drone drone;

    @OneToOne(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Pizza pizza;
}
