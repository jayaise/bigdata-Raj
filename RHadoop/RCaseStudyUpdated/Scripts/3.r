# Create table
rhive.execute("create table RHive_jayaise3.total_sales4 (month string, sales bigint) row format delimited fields terminated by ','")
# Insert data
rhive.execute("insert overwrite table RHive_jayaise3.total_sales4 select month, SUM(recharge_amount) from  RHive_jayaise3.detailed_recharge_details4 where year = '14' group by month")
# Load data
total_sales <- rhive.load.table("RHive_jayaise3.total_sales4")
# Print results
total_sales
# Print summary
summary(total_sales)
# Print str
str(total_sales)
# Check Hive on Hue. Connect to your database, and see the tables.