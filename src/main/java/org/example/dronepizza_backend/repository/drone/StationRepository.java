package org.example.dronepizza_backend.repository.drone;

import org.example.dronepizza_backend.model.drone.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station,Integer> {
}
