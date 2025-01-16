package org.example.dronepizza_backend.service.drone;

import lombok.RequiredArgsConstructor;
import org.example.dronepizza_backend.model.drone.Drone;
import org.example.dronepizza_backend.model.drone.DroneStatus;
import org.example.dronepizza_backend.model.drone.Station;
import org.example.dronepizza_backend.repository.DroneRepository;
import org.example.dronepizza_backend.repository.StationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {

    private final StationRepository stationRepository;
    private final DroneRepository droneRepository;

    @Override
    public Station createStation(String name, float latitude, float longitude) {
        Station station = new Station();
        station.setName(name);
        station.setLatitude(latitude);
        station.setLongitude(longitude);

        return stationRepository.save(station);
    }

    @Override
    public Drone createDrone(){
        Drone drone = new Drone();

        return droneRepository.save(drone);
    }

    @Override
    public List<Drone> getDrones() {
        return droneRepository.findAll();
    }

    @Override
    public void updateStatus(int id, DroneStatus status){
        Drone drone = droneRepository.findById(id).orElse(null);
        if(drone != null){
            drone.setStatus(status);
            droneRepository.save(drone);
        }
    }
}
