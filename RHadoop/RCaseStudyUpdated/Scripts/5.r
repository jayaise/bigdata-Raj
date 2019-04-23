# Open image connection
png(filename="TotalSales.png")
plot(total_sales, col="red", main="Total Sales")
dev.off()
# This plot will be saved in your home directory.
# Open a new terminal, and check the list of files with ls.
# You will find TotalSales.png file.
# Transfer this to your machine using WinSCP or FileZilla
