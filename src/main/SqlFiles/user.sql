CREATE USER 'HuiYan'@'%' IDENTIFIED BY 'HuiYan123456@';
GRANT ALL PRIVILEGES ON ticketdatabase.* TO 'HuiYan'@'%' WITH GRANT OPTION;
flush privileges;

CREATE USER 'mayongzhe'@'%' IDENTIFIED BY 'mayongzhe123';
GRANT ALL PRIVILEGES ON ticketdatabase.* TO 'mayongzhe'@'%' WITH GRANT OPTION;
flush privileges;
