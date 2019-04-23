
import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapred.lib.MultipleInputs;
import org.apache.hadoop.mapred.FileInputFormat;



public class run {
	public static void main(String[] args) throws IOException{
		JobConf conf = new JobConf(run.class);
		conf.setJobName("Relational Join");

		conf.setOutputKeyClass(DoubleWritable.class);
		conf.setOutputValueClass(Text.class);
		
	
		
		conf.setReducerClass(reduce.class);
		
		
		conf.setOutputFormat(TextOutputFormat.class);
		
		
		MultipleInputs.addInputPath(conf, new Path(args[0]), TextInputFormat.class, map_id.class);
		MultipleInputs.addInputPath(conf, new Path(args[1]), TextInputFormat.class, map_trips.class);
		FileOutputFormat.setOutputPath(conf,new Path(args[2]));
		
		
		JobClient.runJob(conf);
		
	}

}
