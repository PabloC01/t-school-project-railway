package com.tsystems.pablo_canton.railway.business;

import com.tsystems.pablo_canton.railway.business.dto.ScheduleDTO;
import com.tsystems.pablo_canton.railway.business.dto.TicketDTO;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.ScheduleEntity;
import com.tsystems.pablo_canton.railway.persistence.jpa.entities.TicketEntity;
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
        mapperFactory.classMap(ScheduleEntity.class, ScheduleDTO.class);
        return mapperFactory.getMapperFacade().map(schedule, ScheduleDTO.class);
    }

    public TicketDTO createTicketDto(TicketEntity seat){
        mapperFactory.classMap(TicketEntity.class, TicketDTO.class);
        return mapperFactory.getMapperFacade().map(seat, TicketDTO.class);
    }
}
