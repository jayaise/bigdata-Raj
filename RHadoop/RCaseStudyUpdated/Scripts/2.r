# Create table
rhive.execute("create table RHive_jayaise3.detailed_recharge_details4 (month string, year string, phone_number string, recharge_amount int, plan_type string, credit int) row format delimited fields terminated by ','")
# Insert Data data
rhive.execute("insert overwrite table RHive_jayaise3.detailed_recharge_details4 select substr(time_stamp,4,2), substr(time_stamp,7,2), phone_number, recharge_amount, plan_type, credit from RHive_jayaise3.customer_recharge_record")
# Query Data
df <- rhive.query("select * from RHive_jayaise3.detailed_recharge_details4 limit 10")
# Print top 6 records
head(df)
# Print summary
summary(df)
# Print str
str(df)
# Check Hive on Hue. Connect to your database, and see the tables.