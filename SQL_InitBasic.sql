use trip_info;
INSERT INTO user (create_time, id, password, role, username) VALUES ('2024-12-07 03:10:33.000000', 1, '1ea11018518411d10d1cb1031fc1991b01f819d1f71dd1b0', 'EMPLOYEE', 'TripWorker');
INSERT INTO user (create_time, id, password, role, username) VALUES ('2024-12-06 20:52:23.104000', 2, '1241a51b21881ce1a017319c17c1ca1c41dd16e192148198', 'CUSTOMER', 'Customer1');
INSERT INTO user (create_time, id, password, role, username) VALUES ('2024-12-06 20:52:23.104000', 3, '1241a51b21881ce1a017319c17c1ca1c41dd16e192148198', 'CUSTOMER', 'Customer2');
INSERT INTO user (create_time, id, password, role, username) VALUES ('2024-12-06 20:52:23.104000', 4, '1241a51b21881ce1a017319c17c1ca1c41dd16e192148198', 'CUSTOMER', 'Customer3');
INSERT INTO user (create_time, id, password, role, username) VALUES ('2024-12-06 20:52:23.104000', 5, '1241a51b21881ce1a017319c17c1ca1c41dd16e192148198', 'CUSTOMER', 'Customer4');
INSERT INTO user (create_time, id, password, role, username) VALUES ('2024-12-06 20:52:23.104000', 6, '1241a51b21881ce1a017319c17c1ca1c41dd16e192148198', 'CUSTOMER', 'Customer5');


INSERT INTO entertainment (floor, entertainment_id, description, entertainment_type, name, status) VALUES (8, 1, 'The theatre offers enchanting symphony and piano performances', 'Theaters', 'Royal Theater', 'ACTIVE');
INSERT INTO entertainment (floor, entertainment_id, description, entertainment_type, name, status) VALUES (10, 2, 'This theater shows different popular and acclaimed movies every day, as well as some classic movies', 'Theaters', 'Movie Theater', 'ACTIVE');
INSERT INTO entertainment (floor, entertainment_id, description, entertainment_type, name, status) VALUES (7, 3, 'Provide you with a variety of entertainment (Note: for adult age over 21)', 'Casino', 'Dynasty Casino', 'ACTIVE');
INSERT INTO entertainment (floor, entertainment_id, description, entertainment_type, name, status) VALUES (3, 4, 'Providing you with a comfortable reading experience', 'Library', 'Jason Library', 'ACTIVE');
INSERT INTO entertainment (floor, entertainment_id, description, entertainment_type, name, status) VALUES (4, 5, 'A very interesting library, offering a variety of sailing books and scenic diaries from various places', 'Library', 'Nautical Library', 'ACTIVE');
INSERT INTO entertainment (floor, entertainment_id, description, entertainment_type, name, status) VALUES (3, 6, 'A safe and fun children\'s playground. (Note: Children under 5 years old must be accompanied by a guardian)', 'Children play', 'Children\'s Park', 'ACTIVE');
INSERT INTO entertainment (floor, entertainment_id, description, entertainment_type, name, status) VALUES (5, 7, 'Basketball / squash / badminton courts available', 'Gym', 'Gym', 'ACTIVE');
INSERT INTO entertainment (floor, entertainment_id, description, entertainment_type, name, status) VALUES (11, 8, '', 'Outdoor pool', 'Outdoor pool', 'ACTIVE');
INSERT INTO entertainment (floor, entertainment_id, description, entertainment_type, name, status) VALUES (9, 9, '', 'Indoor pool', 'Indoor pool', 'ACTIVE');
INSERT INTO entertainment (floor, entertainment_id, description, entertainment_type, name, status) VALUES (11, 10, 'Accommodates 3-4 people', 'Whirlpool', 'Whirlpool', 'ACTIVE');
INSERT INTO entertainment (floor, entertainment_id, description, entertainment_type, name, status) VALUES (9, 11, 'Can accommodate 5-6 people', 'Whirlpool', 'Whirlpool', 'ACTIVE');
INSERT INTO entertainment (floor, entertainment_id, description, entertainment_type, name, status) VALUES (9, 12, 'Enjoy your trip in this premium steam room', 'Steam room', 'Steam room', 'ACTIVE');
INSERT INTO entertainment (floor, entertainment_id, description, entertainment_type, name, status) VALUES (9, 13, 'Enjoy your trip in this premium sona room', 'Sona room', 'Sona room', 'ACTIVE');
INSERT INTO entertainment (floor, entertainment_id, description, entertainment_type, name, status) VALUES (5, 14, 'Enjoy the philosophy of yoga in this spacious Zen room', 'Yoga room', 'Zen room', 'ACTIVE');
INSERT INTO entertainment (floor, entertainment_id, description, entertainment_type, name, status) VALUES (8, 15, 'Note: for adult age over 21', 'Night Club', 'Night Club', 'ACTIVE');
INSERT INTO entertainment (floor, entertainment_id, description, entertainment_type, name, status) VALUES (11, 16, 'Note: for adult age over 21', 'Night Club', 'Night Club', 'ACTIVE');
INSERT INTO entertainment (floor, entertainment_id, description, entertainment_type, name, status) VALUES (11, 17, '2 standard indoor tennis courts, with automatic tennis ball machines and shower rooms', 'Tennis court', 'Tennis court', 'ACTIVE');


INSERT INTO restaurant (floor, restaurant_id, description, name, restaurant_type, serving_time, status) VALUES (6, 1, 'serves Breakfast, Dinner, Lunch', 'Common Buffett', 'Common Buffett', '7 AM to 9 PM', 'ACTIVE');
INSERT INTO restaurant (floor, restaurant_id, description, name, restaurant_type, serving_time, status) VALUES (8, 2, 'serves Dinner only', 'Italian Specialty', 'Italian Specialty', '6 PM to 10 PM', 'ACTIVE');
INSERT INTO restaurant (floor, restaurant_id, description, name, restaurant_type, serving_time, status) VALUES (7, 3, 'serves Dinners only', 'Mexican Specialty', 'Mexican Specialty', '6 PM to 10 PM', 'ACTIVE');
INSERT INTO restaurant (floor, restaurant_id, description, name, restaurant_type, serving_time, status) VALUES (6, 4, 'serves Lunch and Dinner', 'La-carte continental', 'La-carte continental', '12 PM to 8 PM', 'ACTIVE');
INSERT INTO restaurant (floor, restaurant_id, description, name, restaurant_type, serving_time, status) VALUES (5, 5, 'serves Lunch and Dinner', 'Tokyo Ramen Japanese', 'Tokyo Ramen Japanese', '12 PM to 8 PM', 'ACTIVE');
INSERT INTO restaurant (floor, restaurant_id, description, name, restaurant_type, serving_time, status) VALUES (5, 6, 'serves Lunch and Dinner', 'Ming Wok Chinese', 'Ming Wok Chinese', '12 PM to 8 PM', 'ACTIVE');
INSERT INTO restaurant (floor, restaurant_id, description, name, restaurant_type, serving_time, status) VALUES (10, 7, 'serves beverages and light food', 'Round Clock Café', 'Round Clock Café', '24 hours', 'ACTIVE');
INSERT INTO restaurant (floor, restaurant_id, description, name, restaurant_type, serving_time, status) VALUES (10, 8, 'serves alcoholic beverages', 'Pool Bar', 'Pool Bar', '10 AM to 10 PM', 'ACTIVE');
INSERT INTO restaurant (floor, restaurant_id, description, name, restaurant_type, serving_time, status) VALUES (7, 9, 'serves alcoholic beverages', 'Stout Bar', 'Stout Bar', '10 AM to 10 PM', 'ACTIVE');



INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (500, 1, 'Los Angeles International Airport', '425 S Palos Verdes St, San Pedro, CA 90731', 'USA', 'Port of Los Angeles', 'California', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (350, 2, 'Long Beach Airport', '925 Harbor Plaza, Long Beach, CA 90802', 'USA', 'Port of Long Beach', 'California', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (400, 3, 'Seattle-Tacoma International Airport', '2711 Alaskan Way, Seattle, WA 98121', 'USA', 'Port of Seattle', 'Washington', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (600, 4, 'Newark Liberty International Airport', '518 8th Ave, New York, NY 10018', 'USA', 'Port of New York and New Jersey', 'New York/New Jersey', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (300, 5, 'George Bush Intercontinental Airport', '2100 E Loop N, Houston, TX 77029', 'USA', 'Port of Houston', 'Texas', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (700, 6, 'Miami International Airport', '1015 N America Way, Miami, FL 33132', 'USA', 'Port of Miami', 'Florida', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (200, 7, 'Tampa International Airport', '1101 Channelside Dr, Tampa, FL 33602', 'USA', 'Port of Tampa Bay', 'Florida', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (250, 8, 'San Diego International Airport', '849 W Harbor Dr, San Diego, CA 92101', 'USA', 'Port of San Diego', 'California', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (400, 9, 'San Francisco International Airport', 'Port of San Francisco, Embarcadero, San Francisco, CA 94111', 'USA', 'Port of San Francisco', 'California', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (150, 10, 'Baltimore/Washington International Airport', '2001 E Point Rd, Baltimore, MD 21226', 'USA', 'Port of Baltimore', 'Maryland', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (180, 11, 'Charleston International Airport', '196 Concord St, Charleston, SC 29401', 'USA', 'Port of Charleston', 'South Carolina', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (100, 12, 'Baton Rouge Metropolitan Airport', '1100 S River Rd, Baton Rouge, LA 70802', 'USA', 'Port of Baton Rouge', 'Louisiana', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (250, 13, 'Louis Armstrong New Orleans International Airport', '600 Port of New Orleans Pl, New Orleans, LA 70130', 'USA', 'Port of New Orleans', 'Louisiana', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (120, 14, 'Savannah/Hilton Head International Airport', '1000 E President St, Savannah, GA 31404', 'USA', 'Port of Savannah', 'Georgia', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (90, 15, 'Wilmington International Airport', '1710 S 3rd St, Wilmington, NC 28401', 'USA', 'Port of Wilmington', 'North Carolina', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (300, 16, 'Halifax Stanfield International Airport', 'Port of Halifax, 1655 Lower Water St, Halifax, NS B3J 1S3', 'Canada', 'Port of Halifax', 'Nova Scotia', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (500, 17, 'Vancouver International Airport', '999 Canada Place, Vancouver, BC V6C 3T4', 'Canada', 'Port of Vancouver', 'British Columbia', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (400, 18, 'Toronto Pearson International Airport', 'Port of Toronto, 1 York St, Toronto, ON M5J 1B5', 'Canada', 'Port of Toronto', 'Ontario', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (250, 19, 'Montréal-Pierre Elliott Trudeau International Airport', '220 Rue du Port, Montreal, QC H3C 1A9', 'Canada', 'Port of Montreal', 'Quebec', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (100, 20, 'St. John\'s International Airport', 'Port of St. John\'s, St. John\'s, NL A1C 6L2', 'Canada', 'Port of St. John\'s', 'Newfoundland and Labrador', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (200, 21, 'Benito Juárez International Airport', 'Avenida del Puente, Mexico City, CDMX, Mexico', 'Mexico', 'Port of Mexico City', 'Mexico City', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (80, 22, 'General Heriberto Jara International Airport', 'Calle Salvador Díaz Mirón 1600, Veracruz, Mexico', 'Mexico', 'Port of Veracruz', 'Veracruz', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (60, 23, 'Ensenada Airport', 'Boulevard Costero 2, Ensenada, BC, Mexico', 'Mexico', 'Port of Ensenada', 'Baja California', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (150, 24, 'Cozumel International Airport', 'Calle 2 Norte, Cozumel, QR, Mexico', 'Mexico', 'Port of Cozumel', 'Quintana Roo', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (90, 25, 'General Rafael Buelna International Airport', 'Av. del Mar, Mazatlán, Sin, Mexico', 'Mexico', 'Port of Mazatlán', 'Sinaloa', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (300, 26, 'Luis Muñoz Marín International Airport', 'Paseo de la Fortaleza, San Juan, PR 00901', 'Puerto Rico', 'Port of San Juan', 'San Juan', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (120, 27, 'Martinique Aimé Césaire International Airport', 'Place Pierre Nord, Fort-de-France 97200, Martinique', 'Martinique', 'Port of Fort-de-France', 'Fort-de-France', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (250, 28, 'Lynden Pindling International Airport', 'Nassau Harbour, Nassau, Bahamas', 'Bahamas', 'Port of Nassau', 'Nassau', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (150, 29, 'Grantley Adams International Airport', 'Bridgetown Port, Bridgetown, Barbados', 'Barbados', 'Port of Bridgetown', 'Bridgetown', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (80, 30, 'Douglas-Charles Airport', 'Bayfront, Roseau, Dominica', 'Dominica', 'Port of Roseau', 'Roseau', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (100, 31, 'Hewanorra International Airport', 'Vigie Marina, Castries, Saint Lucia', 'Saint Lucia', 'Port of Castries', 'Castries', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (200, 32, 'Norman Manley International Airport', '120-134 Wharf, Kingston, Jamaica', 'Jamaica', 'Port of Kingston', 'Kingston', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (180, 33, 'Cyril E. King Airport', '9-A 99A, St. Thomas, US Virgin Islands', 'U.S. Virgin Islands', 'Port of St. Thomas', 'St. Thomas', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (70, 34, 'Tortola Airport', 'Road Town, Tortola, British Virgin Islands', 'British Virgin Islands', 'Port of Tortola', 'Tortola', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (90, 35, 'V.C. Bird International Airport', 'Heritage Quay, St. John\'s, Antigua', 'Antigua and Barbuda', 'Port of St. John\'s', 'St. John\'s', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (50, 36, 'J. A. Rochebeau Airport', 'Port of Saint-Pierre, Saint-Pierre, Saint Pierre and Miquelon', 'Saint Pierre and Miquelon', 'Port of Saint-Pierre', 'Saint-Pierre', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (200, 37, 'Queen Beatrix International Airport', 'Port of Oranjestad, Aruba', 'Aruba', 'Port of Aruba', 'Aruba', 'ACTIVE');
INSERT INTO port (parking_spots, port_id, nearest_airport, address, country, name, state, status) VALUES (300, 38, 'Hato International Airport', 'Schottegatweg Oost, Willemstad, Curaçao', 'Curaçao', 'Port of Curaçao', 'Curaçao', 'ACTIVE');

INSERT INTO package (price, package_id, description, charge_type, name, status) VALUES (40, 1, '', 'NIGHT', 'Water and Non-Alcoholic', 'ACTIVE');
INSERT INTO package (price, package_id, description, charge_type, name, status) VALUES (80, 2, 'for adult age over 21', 'NIGHT', 'Unlimited Bar', 'ACTIVE');
INSERT INTO package (price, package_id, description, charge_type, name, status) VALUES (150, 3, '', 'TRIP', 'Internet 200 minutes', 'ACTIVE');
INSERT INTO package (price, package_id, description, charge_type, name, status) VALUES (250, 4, '', 'TRIP', 'Unlimited internet', 'ACTIVE');
INSERT INTO package (price, package_id, description, charge_type, name, status) VALUES (60, 5, 'Italian, La-carte, Mexican, Japanese, Chinese', 'NIGHT', 'Specialty dining', 'ACTIVE');

INSERT INTO location (location_id, location) VALUES (1, 'Bow (Forward) - the front of the ship.');
INSERT INTO location (location_id, location) VALUES (2, 'Stern (Aft) - the back of the ship.');
INSERT INTO location (location_id, location) VALUES (3, 'Port Side (Left) - the left side of the ship when facing the bow.');
INSERT INTO location (location_id, location) VALUES (4, 'Starboard Side (Right) -the right side of the ship when facing the bow');


INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (2, 3, 6, 1000, 1, 1, 'The Haven Suite', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (2, 3, 6, 1000, 2, 2, 'The Haven Suite', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (2, 3, 6, 1000, 3, 3, 'The Haven Suite', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (2, 3, 6, 1000, 4, 4, 'The Haven Suite', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (2, 2, 4, 800, 1, 5, 'Club Balcony Suite', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (2, 2, 4, 800, 2, 6, 'Club Balcony Suite', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (2, 2, 4, 800, 3, 7, 'Club Balcony Suite', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (2, 2, 4, 800, 4, 8, 'Club Balcony Suite', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (1, 2, 4, 600, 1, 9, 'Family Large Balcony', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (1, 2, 4, 600, 2, 10, 'Family Large Balcony', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (1, 2, 4, 600, 3, 11, 'Family Large Balcony', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (1, 2, 4, 600, 4, 12, 'Family Large Balcony', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (1, 1.5, 4, 400, 1, 13, 'Family Balcony', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (1, 1.5, 4, 400, 2, 14, 'Family Balcony', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (1, 1.5, 4, 400, 3, 15, 'Family Balcony', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (1, 1.5, 4, 400, 4, 16, 'Family Balcony', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (0, 1, 2, 300, 1, 17, 'Oceanview window', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (0, 1, 2, 300, 2, 18, 'Oceanview window', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (0, 1, 2, 300, 3, 19, 'Oceanview window', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (0, 1, 2, 300, 4, 20, 'Oceanview window', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (0, 1, 2, 200, 1, 21, 'Inside stateroom', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (0, 1, 2, 200, 2, 22, 'Inside stateroom', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (0, 1, 2, 200, 3, 23, 'Inside stateroom', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (0, 1, 2, 200, 4, 24, 'Inside stateroom', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (0, 1, 1, 150, 1, 25, 'Studio stateroom', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (0, 1, 1, 150, 2, 26, 'Studio stateroom', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (0, 1, 1, 150, 3, 27, 'Studio stateroom', 'ACTIVE');
INSERT INTO stateroom (balcony_count, number_bath, number_beds, room_size, location_id, stateroom_id, stateroom_type, status) VALUES (0, 1, 1, 150, 4, 28, 'Studio stateroom', 'ACTIVE');





