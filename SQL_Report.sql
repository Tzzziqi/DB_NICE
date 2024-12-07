use trip_info;

# 1.
# Find the most popular starting ports for ticket booking.
#  Based on the results, the company can decide whether to
#  increase the number of operations and advertising in these ports.
select p.port_id, p.name, p.country, count(distinct b.booking_id) as booking_num
from port p
         left join trip t on p.port_id = t.start_port_id
         left join booking b on t.trip_id = b.trip_id
group by p.port_id, p.name
order by booking_num desc
limit 10;

## Result：
# port_id, name, country, booking_num
# 17,Port of Vancouver,Canada,5
# 32,Port of Kingston,Jamaica,4
# 5,Port of Houston,USA,3
# 34,Port of Tortola,British Virgin Islands,2
# 27,Port of Fort-de-France,Martinique,2
# 15,Port of Wilmington,USA,2
# 22,Port of Veracruz,Mexico,1
# 3,Port of Seattle,USA,1
# 4,Port of New York and New Jersey,USA,1
# 21,Port of Mexico City,Mexico,1



# 2.
# Calculate the proportion of The Haven Suite type rooms in the total room reservations.
# Based on the proportion, we can analyze whether the room distribution on the cruise ship needs to be adjusted later.
select haven_suite_num, total_booked_num, haven_suite_num * 100 / total_booked_num as haven_suite_rate
from (select count(*) as haven_suite_num
      from booking_room_relation
      where stateroom_id in (select stateroom.stateroom_id
                             from stateroom
                             where stateroom_type = 'The Haven Suite')) haven_suite
         cross join
     (select count(*) as total_booked_num
      from booking_room_relation) as total_ordered;

# Result
# haven_suite_num	total_booked_num	haven_suite_rate
# 2	46	4.3478


# 3.
# Finding the trip with the highest selling price for each room type
# can provide a reference for room pricing or improving profit margins by adjusting trip routes, room distribution, etc.
select t.trip_id, t.name, r.stateroom_type, s.price_night
from state_room_price s
         join stateroom r on s.stateroom_id = r.stateroom_id
         join trip t on s.trip_id = t.trip_id
where price_night = (select max(price_night)
                     from state_room_price ns
                              join stateroom nr on ns.stateroom_id = nr.stateroom_id
                     where r.stateroom_type = nr.stateroom_type);

# Result
# trip_id,name,stateroom_type,price_night
# 13,Trip_Middle_2,Oceanview window,900
# 21,The_Middle_8,Inside stateroom,3175
# 23,Trip_Loong_2,Studio stateroom,600
# 23,Trip_Loong_2,Studio stateroom,600
# 23,Trip_Loong_2,Studio stateroom,600
# 23,Trip_Loong_2,Studio stateroom,600

# 4.
# Find all ports that have no trips. For these isolated ports,
# we can consider whether to reduce their local investment or develop trips to this route.
select *
from port
where port_id not in (select start_port_id as port_id
                      from trip
                      union
                      select end_port_id
                      from trip
                      union
                      select port_id
                      from trip_port_info);

# Result
# parking_spots,port_id,nearest_airport,address,country,name,state,status
# 350,2,Long Beach Airport,"925 Harbor Plaza, Long Beach, CA 90802",USA,Port of Long Beach,California,ACTIVE
# 250,28,Lynden Pindling International Airport,"Nassau Harbour, Nassau, Bahamas",Bahamas,Port of Nassau,Nassau,ACTIVE

# 5.
# Statistics on the room sales rate, revenue, and number of tourists of trips in a specified period (in this SQL, the time is > 20 days).
# The subsequent operation strategy can be adjusted according to the trip period with a better revenue ratio
with long_trip as (select trip_id
                   from trip t
                   where t.trip_nights > 20),
     long_trip_booking as (select b.booking_id from booking b where b.trip_id in (select * from long_trip))
select revenue, passengers, booked_room, total_room, booked_room * 100 / total_room as sell_rate
from (select count(*) as total_room from state_room_price sp where sp.trip_id in (select * from long_trip)) tr
         cross join
     (select count(br.stateroom_id) as booked_room
      from booking_room_relation br
      where br.booking_id in (select * from long_trip_booking)) br
         cross join
     (select sum(p.payment_amount) as revenue
      from payment p
      where p.booking_id in (select * from long_trip_booking)) rv
         cross join
     (select count(*) as passengers
      from passenger p
      where p.group_id in
            (select booking_group.group_id
             from booking_group
             where booking_id in (select * from long_trip_booking))) uc;

# Result
# revenue,passengers,booked_room,total_room,sell_rate
# 56944,29,22,146,15.0685

# 6.
# We can also count the top three combinations of passengers’ age/gender distribution.
# Similarly, we can also count which specific regions’ users are most enthusiastic about booking. These can be used as demographic characteristics for our advertising.
select 10 * (year(dob) div 10) as years,
       gender,
       count(1)                as num
from passenger
group by years, gender
order by num desc
limit 3;

# Result
# years,gender,num
# 1990,Male,9
# 1980,Male,7
# 2000,Female,6