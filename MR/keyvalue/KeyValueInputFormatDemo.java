package keyvalue;


import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.KeyValueTextInputFormat;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextOutputFormat;


public class KeyValueInputFormatDemo
{
	public static class Map extends MapReduceBase implements	Mapper<Text, Text, Text, Text> {

		@Override
		public void map(Text key, Text value, OutputCollector<Text, Text> arg2,		Reporter arg3) throws IOException
		{
			//String key = arg0.toString();
			//String value = arg1.toString();
			
			arg2.collect(key, value);
			
		}
		
	}
	
	public static class Reduce extends MapReduceBase implements	Reducer<Text, Text, Text, Text>
	{

		@Override
		public void reduce(Text key, Iterator<Text> values,OutputCollector<Text, Text> output, Reporter arg3)				throws IOException
		{
			
			StringBuffer str = new StringBuffer();
			
			while (values.hasNext()) {
				str.append( values.next().toString());
			}
			
			output.collect(key, new Text(str.toString()));

		}
		
	}
	
	public static void main(String[] args) throws Exception
	{
		
		JobConf conf = new JobConf(KeyValueInputFormatDemo.class);
		conf.setJobName("KeyValueInputFormatDemo");
		
		conf.set("key.value.separator.in.input.line", ","  );
		
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);

		conf.setMapperClass(Map.class);
		conf.setReducerClass(Reduce.class);

		conf.setInputFormat(KeyValueTextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);

		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));

		JobClient.runJob(conf);
		
	}

}
