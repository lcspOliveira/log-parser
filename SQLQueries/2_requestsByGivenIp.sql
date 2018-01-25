--(2) Write MySQL query to find requests made by a given IP.

select request 
from access_log 
where ip_address = :ipAddress;

-- :ipAddress being a ip address, like '192.168.228.188'