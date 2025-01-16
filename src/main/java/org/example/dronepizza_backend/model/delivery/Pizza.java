package org.example.dronepizza_backend.model.delivery;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private float price;

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
}
