library(RHive)
# Setting the library paths
Sys.setenv(HIVE_HOME="/opt/cloudera/parcels/CDH/lib/hive")
Sys.setenv(HADOOP_HOME="/opt/cloudera/parcels/CDH/lib/hadoop")
# Initiate Hive Connection
rhive.init()
# Change the configs if needed. 
rhive.connect("ip-172-31-26-106.us-west-2.compute.internal",defaultFS="hdfs://ip-172-31-20-89.us-west-2.compute.internal:8020", user='jayaise')
# Create data with following schema. Replace username with your linux username
rhive.execute("create database RHive_jayaise3")
# Create table
rhive.execute("create table RHive_jayaise3.customer_recharge_record(time_stamp string, phone_number string, recharge_amount int, plan_type string, credit int) row format delimited fields terminated by ','")
# Load Data
#hadoop fs -cp /tmp/customerdata.csv /user/jayaise
rhive.execute("load data inpath '/user/jayaise/customerdata.csv' into table RHive_jayaise3.customer_recharge_record")
# Check Hive on Hue. Connect to your database, and see the tables.