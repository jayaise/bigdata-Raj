# Create table
rhive.execute("create table RHive_Username.data_sales (year_month string, sales bigint) row format delimited fields terminated by ','")
# Insert data
rhive.execute("insert overwrite table RHive_Username.data_sales select concat(year, month), SUM(recharge_amount) from  RHive_Username.detailed_recharge_details where plan_type = '3G' group by concat(year, month)")
# Load data
data_sales <- rhive.load.table("RHive_Username.data_sales")
# Print results
data_sales
# Print summary
summary(data_sales)
# Print str
str(data_sales)
# Check Hive on Hue. Connect to your database, and see the tables.



