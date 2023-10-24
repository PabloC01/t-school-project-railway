package com.tsystems.pablo_canton.railway.setup.utils;

import com.tsystems.pablo_canton.railway.business.dto.*;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.*;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class Converter {
    private final MapperFactory mapperFactory;

    public Converter(){
        mapperFactory = new DefaultMapperFactory.Builder().build();
    }

    public ScheduleDTO createScheduleDTO(ScheduleEntity schedule){
        mapperFactory.classMap(ScheduleEntity.class, ScheduleDTO.class)
                .field("trainByNumber", "train")
                .field("stationByStartStationId", "startStation")
                .field("stationByEndStationId", "endStation")
                .byDefault().register();
        return mapperFactory.getMapperFacade().map(schedule, ScheduleDTO.class);
    }

    public TicketDTO createTicketDto(TicketEntity seat){
        mapperFactory.classMap(TicketEntity.class, TicketDTO.class)
                .field("userByUserId", "user")
                .field("scheduleByScheduleId", "schedule")
                .byDefault().register();
        return mapperFactory.getMapperFacade().map(seat, TicketDTO.class);
    }

    public TrainDTO createTrainDTO(TrainEntity train) {
        mapperFactory.classMap(TrainEntity.class, TrainDTO.class);
        return mapperFactory.getMapperFacade().map(train, TrainDTO.class);
    }

    public UserDTO createUserDTO(UserEntity user) {
        mapperFactory.classMap(UserEntity.class, UserDTO.class);
        return mapperFactory.getMapperFacade().map(user, UserDTO.class);
    }

    public StationDTO createStationDto(StationEntity station) {
        mapperFactory.classMap(StationEntity.class, StationDTO.class);
        return mapperFactory.getMapperFacade().map(station, StationDTO.class);
    }
}
