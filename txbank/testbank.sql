create database testBank
use testBank;

create table accounts(
   accountid int primary key auto_increment,

   balance numeric (10,2)

);

create table oprecord
(
   id int primary key auto_increment,
   account int references accounts(accountid),
   opmoney numeric (10,2),
   optime datetime,
   optype enum('deposite',,'withdraw','transfer')not null ,
   transferid varchar (50)
);