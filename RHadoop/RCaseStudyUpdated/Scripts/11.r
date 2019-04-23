# Select data
data_recharge_trend = rhive.query("select count(recharge_amount) from RHive_Username.customer_recharge_record where plan_type = '3G' group by recharge_amount")

lbls <- c("199 Plan", "250 Plan", "500 Plan")
pct <- round(data_recharge_trend/sum(data_recharge_trend)*100)
lbls <- paste(lbls, pct) # add percents to labels 
lbls <- paste(lbls,"%",sep="") # ad % to labels 

# Print distribution
table(data_recharge_trend)
# Plot pie chart
# Give the ip address of the machine to which you are connected
png(filename="data_recharge_trend.png")
pie(table(data_recharge_trend),labels = lbls, col=rainbow(length(lbls)),
  	main="Pie Chart of Data Plan Recharge Trends")
dev.off()
# This plot will be save in your home directory.
# Open a new terminal, and check the list of files with ls.
# You will find TotalSales.png file.
# Transfer this to your machine using WinSCP or FileZilla