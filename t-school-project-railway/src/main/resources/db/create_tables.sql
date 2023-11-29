create table users
(
    user_id integer generated always as identity
        constraint user_pk
            primary key,
    username     varchar(50) not null
        constraint user_pk2
            unique,
    password     varchar(150) not null,
    role         varchar(50) not null,
    name         varchar(50),
    surname      varchar(50),
    birth_date   date
);

create table stations
(
    station_id integer     generated always as identity
        constraint station_pk
            primary key,
    name       varchar(50) not null
);

create table trains
(
    train_id          integer generated always as identity
        constraint train_pk
            primary key,
    number            integer not null
        constraint train_pk2
            unique
);

create table schedules
(
    schedule_id      integer   generated always as identity
        constraint schedule_pk
            primary key,
    train_number     integer   not null
        constraint train_number_fk
            references trains (number),
    start_station_id integer   not null
        constraint start_station_fk
            references stations,
    end_station_id   integer
        constraint end_station_fk
            references stations,
    departure_time   timestamp not null,
    arrival_time     timestamp not null,
    constraint arrival_after_departure
        check (arrival_time > departure_time)
);

create table wagons
(
    wagon_number integer not null,
    train_number integer not null
        constraint train_number_fk
            references trains (number),
    seat_count   integer not null,
    seat_per_row integer not null,
    constraint wagon_pk
        primary key (wagon_number, train_number)
);

create table seats
(
    number       integer not null,
    wagon_number integer not null,
    train_number integer not null,
    description  varchar(50),
    constraint seat_pk
        primary key (number, wagon_number, train_number),
    constraint wagon_fk
        foreign key (wagon_number, train_number) references wagons
);

create table tickets
(
    ticket_id    integer generated always as identity
        constraint ticket_pk
            primary key,
    user_id      integer not null
        constraint user_id_fk
            references users,
    seat_number  integer not null,
    wagon_number integer not null,
    train_number integer not null,
    schedule_id  integer not null
        constraint schedule_fk
            references schedules,
    constraint seat_fk
        foreign key (seat_number, wagon_number, train_number) references seats
);