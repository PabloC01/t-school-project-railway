insert into stations (name)
values  ('Madrid'),
        ('Granada');

insert into trains (number)
values  (100);

insert into schedules (train_number, start_station_id, end_station_id, departure_time, arrival_time)
values  (100, 1, 2, '2025-10-05 08:30:00', '2025-10-05 12:00:00'),
        (100, 1, 2, '2020-10-05 08:30:00', '2020-10-05 12:00:00'),
        (100, 1, 2, '2025-10-05 08:30:00', '2025-10-05 12:00:00'),
        (100, 1, 2, '2025-10-05 08:30:00', '2025-10-05 12:00:00');

insert into wagons (wagon_number, train_number, seat_count, seat_per_row)
values  (1, 100, 10, 1);

insert into seats (number, wagon_number, train_number, description)
values  (1, 1, 100, null),
        (2, 1, 100, null),
        (3, 1, 100, null),
        (4, 1, 100, null),
        (5, 1, 100, null),
        (6, 1, 100, null),
        (7, 1, 100, null),
        (8, 1, 100, null),
        (9, 1, 100, null),
        (10, 1, 100, null);

insert into users (username, password, role, name, surname, birth_date)
values  ('test.client', '$2a$10$D8cDT2ro/w7MGrMsq4VgZOHQxAGCRhrDmgp2sk1gxtGodWG5pQNFG', 'C', 'name', 'surname', '2000-01-01'),
        ('test.client2', '$2a$10$D8cDT2ro/w7MGrMsq4VgZOHQxAGCRhrDmgp2sk1gxtGodWG5pQNFG', 'C', 'name', 'surname', '2000-01-01'),
        ('test.employee', '$2a$10$D8cDT2ro/w7MGrMsq4VgZOHQxAGCRhrDmgp2sk1gxtGodWG5pQNFG', 'E', null, null, null);

insert into tickets (user_id, seat_number, wagon_number, train_number, schedule_id)
values  (1, 1, 1, 100, 3),
        (2, 1, 1, 100, 4);