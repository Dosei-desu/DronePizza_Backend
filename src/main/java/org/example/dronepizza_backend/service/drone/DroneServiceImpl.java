package org.example.dronepizza_backend.service.drone;

import lombok.RequiredArgsConstructor;
import org.example.dronepizza_backend.model.drone.Drone;
import org.example.dronepizza_backend.model.drone.DroneStatus;
import org.example.dronepizza_backend.model.drone.Station;
import org.example.dronepizza_backend.repository.drone.DroneRepository;
import org.example.dronepizza_backend.repository.drone.StationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {

    private final StationRepository stationRepository;
    private final DroneRepository droneRepository;

    @Override
    public Station createStation(String name, double latitude, double longitude) {
        Station station = new Station();
        station.setName(name);
        station.setLatitude(latitude);
        station.setLongitude(longitude);

        return stationRepository.save(station);
    }

    @Override
    public Drone createDrone() {
        Drone drone = new Drone();

        //setting drone station
        List<Station> stations = stationRepository.findAll();
        for (int i = 0; i < stations.size(); i++) {
            if (i != 0) {
                if (stations.get(i).getDrones().size() < stations.get(i - 1).getDrones().size()) {
                    drone.setStation(stations.get(i));
                }
            }
            else if (i + 1 != stations.size()) {
                if (stations.get(i).getDrones().size() < stations.get(i + 1).getDrones().size()) {
                    drone.setStation(stations.get(i));
                }
            }
        }
        //if above doesn't trigger (i.e. all stations have either 0 drones or the same amount)
        //then the drone station is set to the first in the list
        if (drone.getStation() == null) {
            drone.setStation(stations.getFirst());
        }

        int randomNumberLength = 8;
        StringBuilder randomNumber = new StringBuilder(randomNumberLength);
        for (int i = 0; i < randomNumberLength; i++) {
            randomNumber.append((int)(Math.random()*10));
        }
        drone.setUUID(randomNumber.toString());

        drone.setStatus(DroneStatus.ENABLED);

        return droneRepository.save(drone);
    }

    @Override
    public List<Drone> getDrones() {
        return droneRepository.findAll();
    }

    @Override
    public void updateStatus(int id, DroneStatus status) {
        Drone drone = droneRepository.findById(id).orElse(null);
        if (drone != null) {
            drone.setStatus(status);
            droneRepository.save(drone);
        }
    }
}
