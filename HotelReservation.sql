CREATE SCHEMA `hotelreservation` ;
CREATE TABLE users (
userid  integer not null,
email varchar(25) not null unique,
passw varchar(425) not null,
typee char(1) not null,
isdeleted char(1) not null,
primary key (userid,email)
);
CREATE TABLE registereduser (
rid  integer not null,
namee varchar(15) not null, 
lastname varchar(15) not null,
pnumber integer,
gender varchar(6),
bdate date not null,
balance int not null,
issendrequest char(1) not null,
primary key (rid),
foreign key (rid) references users(userid) on update cascade
);
CREATE TABLE hotelowner (
hoid  integer not null,
namee varchar(15) not null, 
lastname varchar(15) not null,
pnumber integer,
gender varchar(6),
bdate date not null,
issendrequest char(1) not null,
primary key (hoid),
foreign key (hoid) references users(userid) on update cascade
);

CREATE TABLE hotel (
hid  integer not null,
hownerid integer not null,
namee varchar(15) not null, 
location  varchar(50) not null,
quality integer not null,
costs  integer not null,
vrooms  integer not null,
coste  integer not null,
vroome  integer not null,
costp  integer not null,
vroomp  integer not null,
isactive char(1) not null,
isaccepted char(1) not null,
primary key (hid,namee),
foreign key (hownerid) references hotelowner(hoid) on update cascade
);
CREATE TABLE commentt (
id  integer not null,
hotelid integer not null,
textt varchar(90) not null,
userid integer not null,
isdeleted char(1) not null,
primary key (id),
foreign key (hotelid) references hotel(hid) on update cascade,
foreign key (userid) references registereduser(rid) on update cascade
);
CREATE TABLE reservation (
id  integer not null,
userid integer not null,
hotelid integer not null,
iscancelld char(1) not null,
startdate date not null,
enddate date not null,
numberofroom integer not null,
roomtype char(1) not null,
cost int not null,
primary key (id),
foreign key (hotelid) references hotel(hid) on update cascade,
foreign key (userid) references registereduser(rid) on update cascade
);