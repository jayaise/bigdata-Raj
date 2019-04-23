# select data
plan_type_sales = rhive.query("select SUM(total_recharge_amount) as sales from RHive_Username.user_sales group by plan_type")
print(plan_type_sales)