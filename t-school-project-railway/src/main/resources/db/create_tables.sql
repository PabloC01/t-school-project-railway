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
            unique,
    departure_station integer not null
        constraint departure_station_fk
            references station,
    arrival_station   integer not null
        constraint arrival_station_fk
            references station,
    departure_time    time    not null,
    arrival_time      time    not null
);

alter table train
    owner to postgres;

create table wagon
(
    wagon_number integer not null,
    train_number integer not null
        constraint train_number_fk
            references train (number),
    rows         integer not null,
    seat_per_row integer not null,
    constraint wagon_pk
        primary key (wagon_number, train_number)
);

alter table wagon
    owner to postgres;

create table seat
(
    row          integer not null,
    number       integer not null,
    wagon_number integer not null,
    train_number integer not null,
    available    boolean not null,
    constraint seat_pk
        primary key (row, number, wagon_number, train_number),
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
    user_id integer not null
        constraint user_id_fk
            references "user",
    seat_row     integer not null,
    seat_number  integer not null,
    wagon_number integer not null,
    train_number integer not null,
    constraint seat_fk
        foreign key (seat_row, seat_number, wagon_number, train_number) references seat
);

alter table ticket
    owner to postgres;


