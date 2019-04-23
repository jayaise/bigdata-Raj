-- create database--
create database bank;
show databases;
use bank;
mysql -h 52.34.11.96 -u raj_edu1982 -p --local-infile

---------------------------------
create as per cluster credentials
----------------------------------

-- Savings account mysql table
CREATE TABLE savingsaccount(customerid varchar(11), savingsid varchar(11),avgbalance int(10));

show tables;
desc savingsaccount;

LOAD DATA local INFILE "/home/raj_edu1982/savingsaccount.csv"
INTO TABLE savingsaccount
COLUMNS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

select * from savingsaccount limit 10;

-- loan account mysql table
CREATE TABLE loanaccount(customerid varchar(11), loanid varchar(20), type varchar(20),santionedamount int(10),tenure int(2));

show tables;
desc loanaccount;

LOAD DATA local INFILE "/home/user/1.DataSets/loanaccount.csv"
INTO TABLE loanaccount
COLUMNS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

select * from loanaccount limit 10;

-- deposit account mysql table
CREATE TABLE depositaccount(customerid varchar(11), type varchar(20),tenure int(2));

show tables;
desc depositaccount;

LOAD DATA local INFILE "/home/user/1.DataSets/depositaccount.csv"
INTO TABLE depositaccount
COLUMNS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

select * from depositaccount limit 10;

-- demographics account mysql table
CREATE TABLE demographics(customerid varchar(11), registrationdate varchar(12),age int(5), gender varchar(1), occupation varchar(20), income int(10));

show tables;
desc demographics;

LOAD DATA local INFILE "/home/user/1.DataSets/demographics.csv"
INTO TABLE demographics
COLUMNS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

select * from demographics limit 10;

-- creditcardtrx  mysql table
CREATE TABLE creditcardtrx(customerid varchar(11), trxamount int(5), trxdate varchar(15), trxtype varchar(20));

show tables;
desc creditcardtrx;

LOAD DATA local INFILE "/home/user/1.DataSets/creditcardtrx.csv"
INTO TABLE creditcardtrx
COLUMNS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

select * from creditcardtrx limit 10;

-- creditcard account mysql table
CREATE TABLE creditcard(customerid varchar(11), cardnumber varchar(20), type varchar(15), amountlimit int(10));

show tables;
desc creditcard;

LOAD DATA local INFILE "/home/user/1.DataSets/creditcard.csv"
INTO TABLE creditcard
COLUMNS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

select * from creditcard limit 10;
