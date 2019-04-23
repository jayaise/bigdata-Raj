CREATE TABLE transactions(id varchar(20),chain varchar(20),dept varchar(20), category varchar(20), company varchar(20),
brand varchar(20), date1 varchar(10), productsize int, productmeasure varchar(10), purchasequantity int,
purchaseamount FLOAT);

LOAD DATA INFILE '/home/dmx/transactions_practice.csv' INTO TABLE transactions FIELDS TERMINATED BY ',' 
ENCLOSED BY '"'
LINES TERMINATED BY '\n';

SQOOP - PIG
----------
sqoop import --connect jdbc:mysql://10.242.244.199/training --table transactions --username root --password root123 -m 1 --target-dir /user/dmx/bhagaban/test/test1

transactions = LOAD 'TransactionsData/part-m-00000' USING PigStorage(',') as (id:chararray,chain:chararray,dept:chararray,category:chararray,company:chararray,brand:chararray,date:chararray, productsize:float, productmeasure:chararray, purchasequantity:int, purchaseamount:float);

---
Load data into pig
==============================
transactions = LOAD '/Batch1/project/part-m-00000' USING PigStorage(',') 
as (id:chararray,chain:chararray,dept:chararray,category:chararray,company:chararray,brand:chararray,
date:chararray, productsize:float, productmeasure:chararray, purchasequantity:int, purchaseamount:float);

Top 10 customers
=============================
custGroup = GROUP transactions BY id; --grouping
custSpendings = FOREACH custGroup GENERATE group, SUM(transactions.purchaseamount) as spendings; --sum operation
custSpendingsSort = ORDER custSpendings BY spendings desc;
top10Cust = LIMIT custSpendingsSort 10;
DUMP top10Cust;
STORE top10Cust INTO '/Batch1/project_pig/top10Cust';

Chain wise sales
==============================
chainGroup = GROUP transactions BY chain;
chainSales = FOREACH chainGroup GENERATE group, SUM(transactions.purchasequantity) as totalQuantity, SUM(transactions.purchaseamount) as totalSales;
dump chainSales;
STORE chainSales INTO '/Batch1/project_pig/chainSales';

Each chain, top 10 customers
===================================

chainGroupCust = GROUP transactions BY (chain,id);
chainGroupCustSpedings1 = FOREACH chainGroupCust GENERATE group, SUM(transactions.purchaseamount) as spendings;
chainGroupCustSpendings2= FOREACH chainGroupCustSpedings1 generate group.chain as chain,group.id as id, spendings;
chainGroupCustSpendings3= GROUP chainGroupCustSpendings2 BY chain;
chainTop10Cust = FOREACH chainGroupCustSpendings3{chainGroupCustSpedingsSort = ORDER chainGroupCustSpendings2 BY spendings DESC;
top10Cust = LIMIT chainGroupCustSpedingsSort  10;			
GENERATE top10Cust;			
}
chainTop10Cust = FOREACH chainTop10Cust GENERATE FLATTEN(top10Cust);
STORE chainTop10Cust INTO '/Batch1/project_pig/chainTop10Cust';

Each customer most bought brand
====================================
CustBrandGroup = GROUP transactions BY (id,brand);
CustBrandQuantity = FOREACH CustBrandGroup GENERATE group, SUM(transactions.purchasequantity) as sales;
CustBrandQuantity = FOREACH CustBrandQuantity GENERATE group.brand as brand, group.id as id,  sales;
CustBrandQuantityGroup = GROUP CustBrandQuantity BY brand;
custTop5Brands = FOREACH CustBrandQuantityGroup{CustBrandQuantityGroupSort = ORDER CustBrandQuantity BY sales DESC;
top5Brand = LIMIT CustBrandQuantityGroupSort  5; GENERATE top5Brand;}
custTop5Brands = FOREACH custTop5Brands GENERATE FLATTEN(top5Brand);
STORE custTop5Brands INTO '/Batch1/project_pig/custTopFiveBrands';

Top 10 brands
==============

brandGroup = GROUP transactions BY brand; 
brandPurchase = FOREACH brandGroup GENERATE group, SUM(transactions.purchaseamount) as purchase; 
brandPurchaseSort = ORDER brandPurchase BY purchase desc;
top10Brands = LIMIT brandPurchaseSort 10;
STORE top10Brands INTO '/Batch1/project_pig/top10Brands';

top 10 companies
======================

companyGroup = GROUP transactions BY company;
companyPurchase = FOREACH companyGroup GENERATE group, SUM(transactions.purchaseamount) as purchase; 
companyPurchaseSort = ORDER companyPurchase BY purchase desc;
top10Companies = LIMIT companyPurchaseSort 10;
STORE top10Companies INTO '/Batch1/project_pig/top10Companies';

Chain Year Monthly Sales
============================
chainYearMonSales = FOREACH transactions GENERATE chain,STRSPLIT(date,'/',3),purchaseamount as sales; --(14,(4,21,2013),2.99)
chainYearMonSales = FOREACH chainYearMonSales GENERATE chain, $1.$0 as month, $1.$2 as year, sales;
chainYearMonSalesGroup = GROUP chainYearMonSales by (chain,year,month);
chainYearMonGroupSales = FOREACH chainYearMonSalesGroup GENERATE group, SUM(chainYearMonSales.sales) as totalsales;
chainYearMonGroupSales = FOREACH chainYearMonGroupSales GENERATE group.chain as chain, group.year as year, group.month as month, totalsales;
STORE chainYearMonGroupSales INTO '/Batch1/project_pig/chainYearMonGroupSales';

=====================
HIVE TABLES:
=====================

create external table transactions_staging(id string,chain string,dept string, category string, company string,
brand string, date1 string, productsize int, productmeasure string, purchasequantity int, purchaseamount float)
row format delimited
fields terminated by ','
location '/Batch1/project';

-- Open hive and set below properties
================================
set hive.exec.dynamic.partition.mode=nonstrict;
set hive.exec.dynamic.partition=true;
set hive.enforce.bucketing=true;

-- Create production table and load data
===============================


CREATE TABLE transactions_production
( id string,
dept string,
category string,
company string,
brand string,
date1 string,
productsize int,
productmeasure string,
purchasequantity int,
purchaseamount double)
PARTITIONED BY (chain string)
row format delimited fields terminated by ','
stored as textfile;

Load data into Dynamic partition table
=======================================

INSERT OVERWRITE TABLE transactions_production PARTITION (chain)
select id,dept,category,company,brand,date1,productsize,productmeasure,
purchaseamount,purchaseamount,chain from transactions_staging;

Top 10 customers
==============

select id, sum(purchaseamount) as custSpendings from transactions_production
group by id
sort by custSpendings DESC
limit 10;

Chain wise sales
===============

select chain, sum(purchaseamount), sum(purchasequantity) from transactions_production
group by chain;

Top 10 brands
===============

select brand, sum(purchaseamount) as custSpendings from transactions_production
group by brand
sort by custSpendings DESC
limit 10;

Top 10 companies
==================

select company, sum(purchaseamount) as custSpendings from transactions_production
group by company
sort by custSpendings DESC
limit 10;

Chain Year Monthly Sales
=============================
select chain, split(date1,'/')[2] as year1, split(date1,'/')[0] as month1,
sum(purchaseamount) as totalSales from transactions_production 
group by chain, split(date1,'/')[0],split(date1,'/')[2];

