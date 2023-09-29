-- Search for train from station A(1) to B(2) given time interval (from 8am to 3pm)

SELECT *
FROM train
WHERE departure_station = 1 and arrival_station = 2 and departure_time between '08:00:00' and '15:00:00';

-- List of wagons from a particular train(100)

SELECT *
FROM wagon
WHERE train_number = 100;

-- List of seats in a particular wagon(2 wagon from train 100)

SELECT *
FROM seat
WHERE train_number = 100 and wagon_number = 2;

-- List of passengers registered for a certain train(100)

SELECT passenger.passenger_id, name, surname, birth_date
FROM passenger INNER JOIN public.ticket t on passenger.passenger_id = t.passenger_id
WHERE train_number = 100;