DROP SCHEMA PUBLIC CASCADE;

CREATE TABLE PERSON (
  PersonId int identity PRIMARY KEY not null,
  FirstName varchar(50) not null,
  LastName varchar(50) not null,
  DOB timestamp, 
  Gender varchar(1));

CREATE TABLE Address (
  AddressId int identity PRIMARY KEY not null,
  PersonId int not null,
  Street1 varchar(50) not null,
  Street2 varchar(50),
  City varchar(50) not null,
  State varchar(20) not null,
  Zip varchar(10) not null);


CREATE TABLE Phone (
  PhoneId int identity PRIMARY KEY not null,
  PersonId int not null,
  Number varchar(20) not null,
  Type varchar(20));
  