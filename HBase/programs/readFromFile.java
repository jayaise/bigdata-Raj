package hbase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;


public class readFromFile {
	public static void main(String[] args) throws IOException{
		if(args.length==1)
			{
			Configuration conf = HBaseConfiguration.create(new Configuration());
			HBaseAdmin hba = new HBaseAdmin(conf);
			if(!hba.tableExists(args[0])){
				HTableDescriptor ht = new HTableDescriptor(args[0]);
				ht.addFamily(new HColumnDescriptor("sample"));
				ht.addFamily(new HColumnDescriptor("region"));
				ht.addFamily(new HColumnDescriptor("time"));
				ht.addFamily(new HColumnDescriptor("product"));
				ht.addFamily(new HColumnDescriptor("sale"));
				ht.addFamily(new HColumnDescriptor("profit"));
				hba.createTable(ht);
				System.out.println("New Table Created");
				
				HTable table = new HTable(conf,args[0]);
			
				File f = new File("/home/training/Desktop/inputfile");
				BufferedReader br = new BufferedReader(new FileReader(f));
				String line = br.readLine();
				int i =1;
				String rowname="row";
				while(line!=null && line.length()!=0){
					System.out.println("Ok till here");
					StringTokenizer tokens = new StringTokenizer(line,",");
					rowname = "row"+i;
					Put p = new Put(Bytes.toBytes(rowname));
					p.add(Bytes.toBytes("sample"),Bytes.toBytes("sampleNo."),Bytes.toBytes(tokens.nextToken()));
					p.add(Bytes.toBytes("region"),Bytes.toBytes("country"),Bytes.toBytes(tokens.nextToken()));
					p.add(Bytes.toBytes("region"),Bytes.toBytes("state"),Bytes.toBytes(tokens.nextToken()));
					p.add(Bytes.toBytes("region"),Bytes.toBytes("city"),Bytes.toBytes(tokens.nextToken()));
					p.add(Bytes.toBytes("time"),Bytes.toBytes("year"),Bytes.toBytes(tokens.nextToken()));
					p.add(Bytes.toBytes("time"),Bytes.toBytes("month"),Bytes.toBytes(tokens.nextToken()));
					p.add(Bytes.toBytes("product"),Bytes.toBytes("productNo."),Bytes.toBytes(tokens.nextToken()));
					p.add(Bytes.toBytes("sale"),Bytes.toBytes("quantity"),Bytes.toBytes(tokens.nextToken()));
					p.add(Bytes.toBytes("profit"),Bytes.toBytes("earnings"),Bytes.toBytes(tokens.nextToken()));
					i++;
					table.put(p);
					line = br.readLine();
				}
					br.close();
					table.close();
				}
		 	else
		 		System.out.println("Table Already exists.Please enter another table name");
		}
		else
			System.out.println("Please Enter the table name through command line");		
	}
}