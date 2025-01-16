package org.example.dronepizza_backend.model.delivery;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private double price;

    @OneToOne(mappedBy = "pizza", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Delivery delivery;
}
