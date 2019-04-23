package dc;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;




import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;

public class dcexample {
	
	public static enum count{
		computer,
		civil,
		electrical
	};
	
	public static class MyMapper extends Mapper<LongWritable,Text, Text, Text> {
        
		
		private Map<String, String> depttable = new HashMap<String, String>();
				private Text mykey = new Text();
				private Text myvalue = new Text();
		
		protected void setup(Context context) throws java.io.IOException, InterruptedException{
			Path[] files = DistributedCache.getLocalCacheFiles(context.getConfiguration());
			
			
			for (Path path : files) {
				if (path.getName().equals("dc_dept.txt")) {
					BufferedReader r1 = new BufferedReader(new FileReader(path.toString()));
					String line = r1.readLine();
					while(line != null) {
						String[] tokens = line.split(",");
						String deptid = tokens[0];
						String deptname = tokens[1];
						depttable.put(deptid, deptname);
						line = r1.readLine();
					}
				}
			}
			if (depttable.isEmpty()) {
				throw new IOException("Unable to load Abbrevation data.");
			}
		}

		
        protected void map(LongWritable key, Text value, Context context)
            throws java.io.IOException, InterruptedException {
        	
        	
        	String line = value.toString();
        	String[] tokens = line.split(",");
        	String id = tokens[0];
        	String name = tokens[1];
        	String dept = tokens[2];
        	String out = depttable.get(dept);
        	mykey.set(id+'\t'+name);
        	myvalue.set(out);
        	if(out.equalsIgnoreCase("computers")){
        		context.getCounter(count.computer).increment(1);	
        	}
        	if(out.equalsIgnoreCase("civil")){      	  	
      	  		context.getCounter(count.civil).increment(1);
        	}
        	if(out.equalsIgnoreCase("electricals")){
      	  		context.getCounter(count.electrical).increment(1);
        	}
      	  	context.write(mykey,myvalue);
        }  
}
	
	
  public static void main(String[] args) 
                  throws IOException, ClassNotFoundException, InterruptedException {
    
    Job job = new Job();
    job.setJarByClass(dcexample.class);
    job.setJobName("DC_EXAMPLE");
    job.setNumReduceTasks(0);
    
    try{
    DistributedCache.addCacheFile(new URI("/user/dmx/bhagaban/dc_dept.txt"), job.getConfiguration());
    }catch(Exception e){
    	System.out.println(e);
    }
    
    job.setMapperClass(MyMapper.class);
    
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(Text.class);
    
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    
    job.waitForCompletion(true);
    
Counters counters = job.getCounters();
    
    Counter c1 = counters.findCounter(count.computer);
    System.out.println(c1.getDisplayName()+ " : " + c1.getValue());
    c1 = counters.findCounter(count.electrical);
    System.out.println(c1.getDisplayName()+ " : " + c1.getValue());
    c1 = counters.findCounter(count.civil);
    System.out.println(c1.getDisplayName()+ " : " + c1.getValue());
    
  }
}
