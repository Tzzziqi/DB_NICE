# drop database trip;
# create database trip;
use trip;

# 1ea11018518411d10d1cb1031fc1991b01f819d1f71dd1b0: Worker++
insert into user(username, password, role, create_time)
values ('TripWorker', '1ea11018518411d10d1cb1031fc1991b01f819d1f71dd1b0', 'EMPLOYEE', CURRENT_TIME());


insert into location(location_id, location)
values (1, 'Bow (Forward) - the front of the ship.'),
       (2, 'Stern (Aft) - the back of the ship.'),
       (3, 'Port Side (Left) - the left side of the ship when facing the bow.'),
       (4, 'Starboard Side (Right) -the right side of the ship when facing the bow');

insert into stateroom(stateroom_type, location_id, room_size, number_beds, number_bath, balcony_count, status)
values ('The Haven Suite', 1, 1000, 6, 3, 2, 'ACTIVE'),
       ('The Haven Suite', 2, 1000, 6, 3, 2, 'ACTIVE'),
       ('The Haven Suite', 3, 1000, 6, 3, 2, 'ACTIVE'),
       ('The Haven Suite', 4, 1000, 6, 3, 2, 'ACTIVE'),
       ('Club Balcony Suite', 1, 800, 4, 2, 2, 'ACTIVE'),
       ('Club Balcony Suite', 2, 800, 4, 2, 2, 'ACTIVE'),
       ('Club Balcony Suite', 3, 800, 4, 2, 2, 'ACTIVE'),
       ('Club Balcony Suite', 4, 800, 4, 2, 2, 'ACTIVE'),
       ('Family Large Balcony', 1, 600, 4, 2, 1, 'ACTIVE'),
       ('Family Large Balcony', 2, 600, 4, 2, 1, 'ACTIVE'),
       ('Family Large Balcony', 3, 600, 4, 2, 1, 'ACTIVE'),
       ('Family Large Balcony', 4, 600, 4, 2, 1, 'ACTIVE'),
       ('Family Balcony', 1, 400, 4, 1.5, 1, 'ACTIVE'),
       ('Family Balcony', 2, 400, 4, 1.5, 1, 'ACTIVE'),
       ('Family Balcony', 3, 400, 4, 1.5, 1, 'ACTIVE'),
       ('Family Balcony', 4, 400, 4, 1.5, 1, 'ACTIVE'),
       ('Oceanview window', 1, 300, 2, 1, 0, 'ACTIVE'),
       ('Oceanview window', 2, 300, 2, 1, 0, 'ACTIVE'),
       ('Oceanview window', 3, 300, 2, 1, 0, 'ACTIVE'),
       ('Oceanview window', 4, 300, 2, 1, 0, 'ACTIVE'),
       ('Inside stateroom', 1, 200, 2, 1, 0, 'ACTIVE'),
       ('Inside stateroom', 2, 200, 2, 1, 0, 'ACTIVE'),
       ('Inside stateroom', 3, 200, 2, 1, 0, 'ACTIVE'),
       ('Inside stateroom', 4, 200, 2, 1, 0, 'ACTIVE'),
       ('Studio stateroom', 1, 150, 1, 1, 0, 'ACTIVE'),
       ('Studio stateroom', 2, 150, 1, 1, 0, 'ACTIVE'),
       ('Studio stateroom', 3, 150, 1, 1, 0, 'ACTIVE'),
       ('Studio stateroom', 4, 150, 1, 1, 0, 'ACTIVE');

insert into entertainment(name, description, entertainment_type, floor, status)
values ('Royal Theater', 'The theatre offers enchanting symphony and piano performances', 'Theaters', 8, 'ACTIVE'),
       ('Movie Theater',
        'This theater shows different popular and acclaimed movies every day, as well as some classic movies',
        'Theaters', 10, 'ACTIVE'),
       ('Dynasty Casino', 'Provide you with a variety of entertainment (Note: for adult age over 21)',
        'Casino', 7, 'ACTIVE'),
       ('Jason Library', 'Providing you with a comfortable reading experience', 'Library', 3, 'ACTIVE'),
       ('Nautical Library',
        'A very interesting library, offering a variety of sailing books and scenic diaries from various places',
        'Library', 4, 'ACTIVE'),
       ('Children''s Park',
        'A safe and fun children''s playground. (Note: Children under 5 years old must be accompanied by a guardian)',
        'Children play', 3, 'ACTIVE'),
       ('Gym', 'Basketball / squash / badminton courts available', 'Gym', 5, 'ACTIVE'),
       ('Outdoor pool', '', 'Outdoor pool', 11, 'ACTIVE'),
       ('Indoor pool', '', 'Indoor pool', 9, 'ACTIVE'),
       ('Whirlpool', 'Accommodates 3-4 people', 'Whirlpool', 11, 'ACTIVE'),
       ('Whirlpool', 'Can accommodate 5-6 people', 'Whirlpool', 9, 'ACTIVE'),
       ('Steam room', 'Enjoy your trip in this premium steam room', 'Steam room', 9, 'ACTIVE'),
       ('Sona room', 'Enjoy your trip in this premium sona room', 'Sona room', 9, 'ACTIVE'),
       ('Zen room', 'Enjoy the philosophy of yoga in this spacious Zen room', 'Yoga room', 5, 'ACTIVE'),
       ('Night Club', 'Note: for adult age over 21', 'Night Club', 8, 'ACTIVE'),
       ('Night Club', 'Note: for adult age over 21', 'Night Club', 11, 'ACTIVE'),
       ('Tennis court', '2 standard indoor tennis courts, with automatic tennis ball machines and shower rooms',
        'Tennis court', 11, 'ACTIVE');

insert into restaurant(name, description, restaurant_type, floor, serving_time, status)
values ('Common Buffett', 'serves Breakfast, Dinner, Lunch', 'Common Buffett', 6, '7 AM to 9 PM', 'ACTIVE'),
       ('Italian Specialty', 'serves Dinner only', 'Italian Specialty', 8, '6 PM to 10 PM', 'ACTIVE'),
       ('Mexican Specialty', 'serves Dinners only', 'Mexican Specialty', 7, '6 PM to 10 PM', 'ACTIVE'),
       ('La-carte continental', 'serves Lunch and Dinner', 'La-carte continental', 6, '12 PM to 8 PM', 'ACTIVE'),
       ('Tokyo Ramen Japanese', 'serves Lunch and Dinner', 'Tokyo Ramen Japanese', 5, '12 PM to 8 PM', 'ACTIVE'),
       ('Ming Wok Chinese', 'serves Lunch and Dinner', 'Ming Wok Chinese', 5, '12 PM to 8 PM', 'ACTIVE'),
       ('Round Clock Café', 'serves beverages and light food', 'Round Clock Café', 10, '24 hours', 'ACTIVE'),
       ('Pool Bar', 'serves alcoholic beverages', 'Pool Bar', 10, '10 AM to 10 PM', 'ACTIVE'),
       ('Stout Bar', 'serves alcoholic beverages', 'Stout Bar', 7, '10 AM to 10 PM', 'ACTIVE')
;

insert into package(name, description, charge_type, price, status)
values ('Water and Non-Alcoholic', '', 'NIGHT', 40, 'ACTIVE'),
       ('Unlimited Bar', 'for adult age over 21', 'NIGHT', 80, 'ACTIVE'),
       ('Internet 200 minutes', '', 'TRIP', 150, 'ACTIVE'),
       ('Unlimited internet', '', 'TRIP', 250, 'ACTIVE'),
       ('Specialty dining', 'Italian, La-carte, Mexican, Japanese, Chinese', 'NIGHT', 60, 'ACTIVE')
;