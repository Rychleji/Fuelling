drop table CAR if exists;
create table CAR(LICENCE_PLATE varchar(8) primary key not null,
	COLOUR varchar(20) not null,
	CURRENT_MILEAGE int);
	
insert into CAR values ('1A1 1010', 'red', 10210);
insert into CAR values ('1A2 2121', 'blue', 245700);
insert into CAR values ('7H1 4651', 'red', 1024);
insert into CAR values ('2B6 4123', 'yellow', 25452);
insert into CAR values ('3P1 2145', 'black', 452);
insert into CAR values ('5K4 1410', 'blue', 452345);
insert into CAR values ('1H1 1994', 'green', 45645);
insert into CAR values ('9M2 0126', 'silver', 0);

drop table FUELLING if exists;
create table FUELLING(ID identity primary key,
	LITRES real not null,
	PRICE_PER_LITRE decimal(3,1) not null,
	CITY varchar(60) not null,
	REFUELLED_CAR varchar(8) not null,
	foreign key (REFUELLED_CAR) references CAR(LICENCE_PLATE) on delete cascade on update cascade);
	
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (30.1, 30.0, 'Náchod', '1A1 1010');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (27.8, 28.5, 'Hronov', '1A1 1010');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (26.7, 28.9, 'Náchod', '1A1 1010');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (28.1, 33.0, 'Náchod', '1A2 2121');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (23.0, 33.6, 'Česká Skalice', '1A2 2121');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (28.5, 28.4, 'Hronov', '1A2 2121');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (35.0, 31.8, 'Náchod', '7H1 4651');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (36.7, 32.9, 'Náchod', '7H1 4651');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (31.1, 30.5, 'Náchod', '2B6 4123');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (36.3, 28.3, 'Praha', '2B6 4123');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (25.0, 28.0, 'Pardubice', '2B6 4123');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (21.8, 32.5, 'Hronov', '2B6 4123');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (36.4, 28.2, 'Náchod', '2B6 4123');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (36.4, 31.4, 'Náchod', '3P1 2145');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (32.5, 34.7, 'Náchod', '3P1 2145');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (24.0, 32.1, 'Náchod', '3P1 2145');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (20.3, 34.3, 'Velké Poříčí', '3P1 2145');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (27.5, 29.0, 'Náchod', '5K4 1410');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (38.9, 34.9, 'Náchod', '5K4 1410');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (21.1, 32.0, 'Náchod', '1H1 1994');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (39.6, 34.9, 'Hradec Králové', '1H1 1994');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (28.0, 33.1, 'Náchod', '1H1 1994');
insert into FUELLING(LITRES, PRICE_PER_LITRE, CITY, REFUELLED_CAR) values (30.1, 33.5, 'Náchod', '1H1 1994');