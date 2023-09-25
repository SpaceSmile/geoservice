-- create database "geoservice" with owner postgres;

create extension if not exists "uuid-ossp";

create table if not exists geo
(
    id UUID unique not null default uuid_generate_v1() primary key,
    countryName varchar(50) unique not null,
    countryCode varchar(2) unique not null,
    countryCoordinate json

);

alter table geoservice
    owner to postgres;