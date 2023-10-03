create table "user"
(
    user_id integer not null
        constraint passenger_pk
            primary key,
    username     varchar(50) not null
        constraint passenger_pk2
            unique,
    password     varchar(50) not null,
    role         varchar(50) not null,
    name         varchar(50),
    surname      varchar(50),
    birth_date   date
);

alter table "user"
    owner to postgres;

create table station
(
    station_id integer     not null
        constraint station_pk
            primary key,
    name       varchar(50) not null
);

alter table station
    owner to postgres;

create table train
(
    train_id          integer not null
        constraint train_pk
            primary key,
    number            integer not null
        constraint "Train_pk2"
            unique
);

alter table train
    owner to postgres;

create table schedule
(
    schedule_id      integer not null
        constraint schedule_pk
            primary key,
    train_number     integer not null
        constraint train_number_fk
            references train (number),
    start_station_id integer not null
        constraint start_station_fk
            references station,
    end_station_id   integer
        constraint end_station_fk
            references station,
    departure_time   time    not null,
    arrival_time     time    not null
);

alter table schedule
    owner to postgres;

create table wagon
(
    wagon_number integer not null,
    train_number integer not null
        constraint train_number_fk
            references train (number),
    seat_count   integer not null,
    seat_per_row integer not null,
    constraint wagon_pk
        primary key (wagon_number, train_number)
);

alter table wagon
    owner to postgres;

create table seat
(
    number       integer not null,
    wagon_number integer not null,
    train_number integer not null,
    description  varchar(50),
    constraint seat_pk
        primary key (number, wagon_number, train_number),
    constraint wagon_fk
        foreign key (wagon_number, train_number) references wagon
);

alter table seat
    owner to postgres;

create table ticket
(
    ticket_id    integer not null
        constraint ticket_pk
            primary key,
    user_id      integer not null
        constraint user_id_fk
            references "user",
    seat_number  integer not null,
    wagon_number integer not null,
    train_number integer not null,
    schedule_id  integer not null
        constraint schedule_fk
            references schedule,
    constraint seat_fk
        foreign key (seat_number, wagon_number, train_number) references seat
);

alter table ticket
    owner to postgres;


