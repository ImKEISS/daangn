DROP DATABASE IF EXISTS  daangnDB;
DROP USER IF EXISTS  daangn@localhost;
create user daangn@localhost identified WITH mysql_native_password  by 'daangn';
create database daangnDB;
grant all privileges on daangn.* to daangn@localhost with grant option;
commit;

USE daangnDB;

CREATE TABLE Regions (
	regionid 	INTEGER PRIMARY KEY,
	province	VARCHAR(10) NOT NULL,
	district  	VARCHAR(10) NOT NULL,
	town 		VARCHAR(10) NOT NULL
);

CREATE TABLE Users (
	userid      			VARCHAR(20) PRIMARY KEY,
	nickname    			VARCHAR(10) NOT NULL,
	manner_temperature   	FLOAT DEFAULT 36.5,
	residence				INTEGER NOT NULL,	-- regionid를 저장함
	FOREIGN KEY (residence) REFERENCES Regions(regionid)
);

CREATE TABLE  Posts (
	postid    	INTEGER PRIMARY KEY AUTO_INCREMENT,
	title     	VARCHAR(255) NOT NULL,
	price     	INTEGER NOT NULL,
	posttime	TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	content   	TEXT NOT NULL,
    deal_status VARCHAR(4) DEFAULT '거래중',
	userid		VARCHAR(20) NOT NULL,
	FOREIGN KEY (userid) REFERENCES Users(userid)
);