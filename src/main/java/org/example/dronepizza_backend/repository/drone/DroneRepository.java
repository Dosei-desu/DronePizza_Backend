package org.example.dronepizza_backend.repository.drone;

import org.example.dronepizza_backend.model.drone.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone,Integer> {

}
