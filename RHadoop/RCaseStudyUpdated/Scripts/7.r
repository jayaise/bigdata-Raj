# Create table
rhive.execute("create table RHive_Username.user_sales as select phone_number, plan_type, SUM(recharge_amount) as total_recharge_amount from RHive_Username.customer_recharge_record group by phone_number, plan_type")
# Insert data
user_sales = rhive.query("select phone_number, SUM(total_recharge_amount) as total_recharge_amount from RHive_Username.user_sales group by phone_number limit 10")
# Results check
user_sales

# Print summary
summary(user_sales)
# Print str
str(user_sales)
# Check Hive on Hue. Connect to your database, and see the tables.

