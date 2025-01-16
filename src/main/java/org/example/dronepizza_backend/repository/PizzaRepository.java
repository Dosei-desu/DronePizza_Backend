package org.example.dronepizza_backend.repository;

import org.example.dronepizza_backend.model.delivery.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza,Integer> {
}
