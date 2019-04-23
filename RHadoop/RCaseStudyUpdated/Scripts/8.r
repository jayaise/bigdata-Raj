# Create table
rhive.execute("create table RHive_Username.user_data_sales (phone_number string, data_recharge_amount bigint) row format delimited fields terminated by ','")
# Create table
rhive.execute("insert overwrite table RHive_Username.user_data_sales select phone_number, total_recharge_amount from RHive_Username.user_sales where plan_type = '3G'")
# Select top 20 records
user_data_sales = rhive.query("select * RHive_Username.from user_data_sales limit 20")
print(user_data_sales)

# Order data
top_data_users = rhive.query("select * from RHive_Username.user_data_sales order by data_recharge_amount desc limit 10")
print(top_data_users)

# create table
rhive.execute("create table RHive_Username.user_talktime_sales (phone_number string, talktime_recharge_amount bigint) row format delimited fields terminated by ','")

# Load data
rhive.execute("insert overwrite table RHive_Username.user_talktime_sales select phone_number, total_recharge_amount RHive_Username.from user_sales where plan_type = 'TT'")

# Select top 20 records
user_talktime_sales = rhive.query("select * from RHive_Username.user_talktime_sales limit 20")
print(user_talktime_sales)
# Order the results
top_talktime_users = rhive.query("select * from RHive_Username.user_talktime_sales order by talktime_recharge_amount desc limit 10")
print(top_talktime_users)

