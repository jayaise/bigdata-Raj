

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.SequenceFileInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;



public class sortrunner {
	public static void main(String[] args) throws IOException{
		JobConf conf = new JobConf(sortrunner.class);
		conf.setJobName("Sort");

		conf.setOutputValueClass(Text.class);
		conf.setOutputKeyClass(DoubleWritable.class);
		
		conf.setMapperClass(sortmap.class);
		conf.setReducerClass(sortreducer.class);
		
		conf.setInputFormat(SequenceFileInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);
		
		FileInputFormat.setInputPaths(conf,new Path(args[0]));
		FileOutputFormat.setOutputPath(conf,new Path(args[1]));
		
		JobClient.runJob(conf);
	}

}
