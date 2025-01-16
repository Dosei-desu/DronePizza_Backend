package org.example.dronepizza_backend.service.drone;

import org.example.dronepizza_backend.model.drone.Drone;
import org.example.dronepizza_backend.model.drone.DroneStatus;
import org.example.dronepizza_backend.model.drone.Station;

import java.util.List;

public interface DroneService {
    Station createStation(String name, double latitude, double longitude);

    Drone createDrone();

    List<Drone> getDrones();

    void updateStatus(int id, DroneStatus status);
}
