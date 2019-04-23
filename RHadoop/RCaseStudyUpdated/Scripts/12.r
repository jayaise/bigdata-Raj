
# Select data
talktime_recharge_trend = rhive.query("select count(recharge_amount) from RHive_Username.customer_recharge_record where plan_type = 'TT' group by recharge_amount")

lbls <- c("50 Plan", "150 Plan", "200 Plan")
pct <- round(talktime_recharge_trend/sum(talktime_recharge_trend)*100)
lbls <- paste(lbls, pct) # add percents to labels 
lbls <- paste(lbls,"%",sep="") # ad % to labels 
# Give the ip address of the machine to which you are connected
png(filename="talktime_recharge_trend.png")
pie(table(talktime_recharge_trend),labels = lbls, col=rainbow(length(lbls)),
  	main="Pie Chart of Talktime Plan Recharge Trends")
dev.off()
# This plot will be save in your home directory.
# Open a new terminal, and check the list of files with ls.
# You will find TotalSales.png file.
# Transfer this to your machine using WinSCP or FileZilla