-- (1) Write MySQL query to find IPs that mode more than a certain number of requests for a given time period.


select ip_address from access_log 
where date_time between :startDateTime and :endDateTime
group by ip_address 
having count(id) > :threshold;

-- :startDateTime being a dateTime, like '2017-01-01T13:00:00'
-- :endDateTime being a dateTime, like '2017-01-01T14:00:00'
-- :threshold being an integer, like 100