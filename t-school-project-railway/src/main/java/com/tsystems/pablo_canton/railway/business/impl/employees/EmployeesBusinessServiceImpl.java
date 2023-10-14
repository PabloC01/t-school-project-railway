package com.tsystems.pablo_canton.railway.business.impl.employees;

import com.tsystems.pablo_canton.railway.business.api.employees.IEmployeesBusinessService;
import com.tsystems.pablo_canton.railway.business.dto.*;
import com.tsystems.pablo_canton.railway.persistence.api.employees.IEmployeesDataService;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.*;
import com.tsystems.pablo_canton.railway.utils.Converter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeesBusinessServiceImpl implements IEmployeesBusinessService {

    private final IEmployeesDataService employeesDataService;

    private final Converter converter;

    @Override
    public List<TrainDTO> getTrains() {
        return employeesDataService.findTrains().stream()
                .map(converter::createTrainDTO)
                .toList();
    }

    @Override
    public List<ScheduleDTO> getSchedules() {
        return employeesDataService.findSchedules().stream()
                .map(converter::createScheduleDTO)
                .toList();
    }

    @Override
    public List<UserDTO> getPassengers(Integer scheduleId) {
        return employeesDataService.findPassengers(scheduleId).stream()
                .map(converter::createUserDTO)
                .toList();
    }

    @Override
    public StationDTO createStation(StationDTO dto) {
        StationEntity station = new StationEntity();
        station.setName(dto.getName());

        return converter.createStationDto(employeesDataService.createStation(station));
    }

    @Override
    public ScheduleDTO createSchedule(ScheduleDTO dto) {
        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setArrivalTime(dto.getArrivalTime());
        schedule.setDepartureTime(dto.getDepartureTime());
        schedule.setTrainByNumber(employeesDataService.loadTrain(dto.getTrainByNumber().getTrainId()));
        schedule.setStationByStartStationId(employeesDataService.loadStation(dto.getStationByStartStationId().getStationId()));
        schedule.setStationByEndStationId(employeesDataService.loadStation(dto.getStationByEndStationId().getStationId()));

        return converter.createScheduleDTO(employeesDataService.createSchedule(schedule));
    }

    @Override
    public TrainDTO createTrain(TrainWagons dto) {
        TrainEntity train = new TrainEntity();
        train.setNumber(dto.getTrain().getNumber());

        train = employeesDataService.createTrain(train);

        int wagonNumber = 1;
        for(WagonDTO wagonDTO : dto.getWagons()){
            WagonEntity wagon = new WagonEntity();
            wagon.setTrainByNumber(train);
            wagon.setWagonNumber(wagonNumber);
            wagon.setTrainNumber(train.getNumber());
            wagon.setSeatCount(wagonDTO.getSeatCount());
            wagon.setSeatPerRow(wagonDTO.getSeatPerRow());

            wagon = employeesDataService.createWagon(wagon);

            for(int seatNumber = 1; seatNumber <= wagon.getSeatCount(); seatNumber++){
                SeatEntity seat = new SeatEntity();
                seat.setTrainNumber(train.getNumber());
                seat.setNumber(seatNumber);
                seat.setWagonNumber(wagon.getWagonNumber());
                seat.setWagon(wagon);

                employeesDataService.createSeat(seat);
            }

            wagonNumber += 1;
        }

        return converter.createTrainDTO(train);
    }
}
