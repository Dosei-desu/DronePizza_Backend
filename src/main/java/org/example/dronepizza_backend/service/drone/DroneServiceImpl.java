package org.example.dronepizza_backend.service.drone;

import lombok.RequiredArgsConstructor;
import org.example.dronepizza_backend.repository.DroneRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DroneServiceImpl {

    private final DroneRepository droneRepository;
}
