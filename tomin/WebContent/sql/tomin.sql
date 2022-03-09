CREATE DATABASE IF NOT EXISTS tomin
DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE tomin;

CREATE TABLE IF NOT EXISTS user (
userEmail VARCHAR(50) PRIMARY KEY,
userNick VARCHAR(20) UNIQUE,
userPassword VARCHAR(64),
userImg VARCHAR(1024),
userIntro VARCHAR(1024),
userEmailHash VARCHAR(64),
userEmailChecked BOOLEAN
);

CREATE TABLE IF NOT EXISTS stop (
stopID int primary key,
stopDays int,
userEmail varchar(50),
stopDate varchar(20),
stopDateEnd varchar(20)
);

CREATE TABLE IF NOT EXISTS share (
shareID INT PRIMARY KEY,
shareImg VARCHAR(1024),
shareContent VARCHAR(2048),
shareDate DATETIME,
likeCount INT,
userEmail VARCHAR(20),
userNick VARCHAR(20),
userImg VARCHAR(1024),
boardType VARCHAR(20) DEFAULT "share"
);

CREATE TABLE IF NOT EXISTS schedule (
scheduleID INT PRIMARY KEY,
scheduleTitle VARCHAR(50),
scheduleContent VARCHAR(2048),
scheduleDate DATETIME,
userEmail VARCHAR(20),
boardType VARCHAR(20) DEFAULT "schedule"
);

CREATE TABLE IF NOT EXISTS notice (
noticeID INT PRIMARY KEY,
noticeTitle VARCHAR(50),
noticeContent VARCHAR(2048),
noticeDate DATETIME
);

CREATE TABLE IF NOT EXISTS likey (
userEmail VARCHAR(20),
shareID INT,
PRIMARY KEY (userEmail, shareID)
);

CREATE TABLE IF NOT EXISTS report (
reportID int primary key,
userEmail varchar(20),
reportTarget varchar(20),
reportReason varchar(20),
reportContent varchar(1024),
reportAddr varchar(100),
reportDate datetime
);