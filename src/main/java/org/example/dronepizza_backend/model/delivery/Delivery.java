package org.example.dronepizza_backend.model.delivery;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.dronepizza_backend.model.drone.Drone;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
    private LocalDateTime expectedDeliveryTime;
    private LocalDateTime actualDeliveryTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "drone_id")
    private Drone drone;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pizza_id")
    private Pizza pizza;
}
