# create table, load data
rhive.execute("create table avg_recharge as select phone_number, (total_recharge_amount/12) as monthly_avg_recharge, plan_type from RHive_Username.user_sales")
# create table, load data
rhive.execute("create table RHive_Username.combo_offer_list as select phone_number, x.monthly_recharge from (select phone_number, SUM(monthly_avg_recharge) as monthly_recharge from RHive_Username.avg_recharge group by phone_number) x where x.monthly_recharge >= 500")
# select data
combo_offer_list = rhive.query("select * from RHive_Username.combo_offer_list limit 10")
print(combo_offer_list)
combo_offer_user_list = rhive.query("select phone_number from RHive_Username.combo_offer_list")
print(combo_offer_user_list)