package com.tsystems.pablo_canton.railway.business.impl.employees;

import com.tsystems.pablo_canton.railway.business.api.employees.IEmployeesBusinessService;
import com.tsystems.pablo_canton.railway.business.dto.*;
import com.tsystems.pablo_canton.railway.persistence.api.employees.IEmployeesDataService;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.*;
import com.tsystems.pablo_canton.railway.setup.exception.StationNameNotAvailableException;
import com.tsystems.pablo_canton.railway.setup.exception.TrainNumberNotAvailableException;
import com.tsystems.pablo_canton.railway.setup.utils.Converter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeesBusinessServiceImpl implements IEmployeesBusinessService {

    private final IEmployeesDataService employeesDataService;

    private final Converter converter;

    @Override
    public List<TrainInfo> getTrains() {
        List<TrainInfo> trainsInfo = new ArrayList<>();
        List<TrainEntity> trains = employeesDataService.findTrains();

        for(TrainEntity train : trains){
            TrainInfo info = new TrainInfo();

            info.setTrainId(train.getTrainId());
            info.setNumber(train.getNumber());
            info.setWagonCount(train.getWagonsByNumber().size());
            info.setSeatCount(train.getWagonsByNumber().stream().toList().get(0).getSeatCount());

            trainsInfo.add(info);
        }

        return trainsInfo;
    }

    @Override
    public List<ScheduleDTO> getSchedules() {
        return employeesDataService.findSchedules().stream()
                .map(converter::createScheduleDTO)
                .toList();
    }

    @Override
    public List<StationDTO> getStations() {
        return employeesDataService.findStations().stream()
                .map(converter::createStationDto)
                .toList();
    }

    @Override
    public List<UserInfo> getPassengers(Integer scheduleId) {
        List<UserInfo> usersInfo = new ArrayList<>();
        List<UserEntity> users = employeesDataService.findPassengers(scheduleId);

        for(UserEntity user : users){
            UserInfo info = new UserInfo();

            info.setUsername(user.getUsername());
            info.setName(user.getName());
            info.setSurname(user.getSurname());
            info.setBirthDate(user.getBirthDate());

            usersInfo.add(info);
        }

        return usersInfo;
    }

    @Override
    public StationDTO createStation(StationDTO dto) {
        StationEntity station = new StationEntity();
        String name = dto.getName();

        if(employeesDataService.isStationNameAvailable(name)){
            throw new StationNameNotAvailableException("Station name already exists " + name);
        }

        station.setName(name);

        return converter.createStationDto(employeesDataService.createStation(station));
    }

    @Override
    public ScheduleDTO createSchedule(ScheduleDTO dto) {
        ScheduleEntity schedule = new ScheduleEntity();

        schedule.setArrivalTime(dto.getArrivalTime());
        schedule.setDepartureTime(dto.getDepartureTime());
        schedule.setTrainByNumber(employeesDataService.loadTrain(dto.getTrain().getNumber()));
        schedule.setStationByStartStationId(employeesDataService.loadStation(dto.getStartStation().getName()));
        schedule.setStationByEndStationId(employeesDataService.loadStation(dto.getEndStation().getName()));

        return converter.createScheduleDTO(employeesDataService.createSchedule(schedule));
    }

    @Override
    public TrainDTO createTrain(TrainWagons dto) {
        TrainEntity train = new TrainEntity();
        Integer trainNumber = dto.getTrain().getNumber();

        if(employeesDataService.isTrainNumberAvailable(trainNumber)){
            throw new TrainNumberNotAvailableException("Train number already exists " + trainNumber);
        }

        train.setNumber(trainNumber);
        train = employeesDataService.createTrain(train);

        int wagonNumber = 1;
        for(WagonDTO wagonDTO : dto.getWagons()){
            WagonEntity wagon = new WagonEntity();
            wagon.setTrainByNumber(train);
            wagon.setWagonNumber(wagonNumber);
            wagon.setTrainNumber(trainNumber);
            wagon.setSeatCount(wagonDTO.getSeatCount());
            wagon.setSeatPerRow(wagonDTO.getSeatPerRow());

            wagon = employeesDataService.createWagon(wagon);

            for(int seatNumber = 1; seatNumber <= wagon.getSeatCount(); seatNumber++){
                SeatEntity seat = new SeatEntity();
                seat.setTrainNumber(trainNumber);
                seat.setNumber(seatNumber);
                seat.setWagonNumber(wagon.getWagonNumber());
                seat.setWagon(wagon);

                employeesDataService.createSeat(seat);
            }

            wagonNumber += 1;
        }

        return converter.createTrainDTO(train);
    }

    @Override
    public List<String> getStationNames() {
        return employeesDataService.findStationNames();
    }
}
