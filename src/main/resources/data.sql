CREATE TABLE MOVIE("ID" bigint auto_increment, "YEAR" bigint, "TITLE" varchar(255), "STUDIOS" varchar(255), "PRODUCERS" varchar(255), "WINNER" boolean);

insert into movie("YEAR", "TITLE", "STUDIOS", "PRODUCERS", "WINNER")
select "YEAR", "TITLE", "STUDIOS", "PRODUCERS", "WINNER" FROM CSVREAD('classpath:static/movielist.csv',null,'fieldSeparator=;')