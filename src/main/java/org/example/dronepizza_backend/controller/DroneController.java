package org.example.dronepizza_backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.dronepizza_backend.model.drone.Drone;
import org.example.dronepizza_backend.model.drone.DroneStatus;
import org.example.dronepizza_backend.service.drone.DroneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drones")
public class DroneController {

    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;

        //couldn't get Data.sql to work with inserts, so hardcoding it here | TODO fix
        droneService.createStation("alpha",55.41,12.34);
        droneService.createStation("bravo",55.39,12.32);
        droneService.createStation("charlie",55.41,12.37);
    }

    @GetMapping("")
    public ResponseEntity<List<Drone>> getDrones() {
        List<Drone> drones = droneService.getDrones();

        if(drones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(drones);
    }

    @GetMapping("/add")
    public ResponseEntity<Drone> addDrone() {
        Drone drone = droneService.createDrone();
        return ResponseEntity.ok(drone);
    }

    @GetMapping("/{id}/enable")
    public ResponseEntity<Drone> enableDrone(@PathVariable int id) {
        droneService.updateStatus(id, DroneStatus.ENABLED);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/disable")
    public ResponseEntity<Drone> disableDrone(@PathVariable int id) {
        droneService.updateStatus(id, DroneStatus.DISABLED);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/retire")
    public ResponseEntity<Drone> retireDrone(@PathVariable int id) {
        droneService.updateStatus(id, DroneStatus.RETIRED);
        return ResponseEntity.noContent().build();
    }

}
