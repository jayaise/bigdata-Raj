# Select data
user_data_sales = rhive.query("select * from RHive_Username.user_data_sales limit 10")

# Open plot connection
png(filename="user_data_sales.png")
plot(user_data_sales, col="red", main="User Data Recharge")
dev.off()
# This plot will be save in your home directory.
# Open a new terminal, and check the list of files with ls.
# You will find TotalSales.png file.
# Transfer this to your machine using WinSCP or FileZilla