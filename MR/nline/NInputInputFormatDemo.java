package nline;



import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
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
import org.apache.hadoop.mapred.lib.NLineInputFormat;


public class NInputInputFormatDemo
{
	public static class Map extends MapReduceBase implements	Mapper<LongWritable, Text, Text, Text> {

		@Override
		public void map(LongWritable arg0, Text arg1,
				OutputCollector<Text, Text> output, Reporter arg3)
				throws IOException
		{
			String line = arg1.toString();
			String [] word = line.split(",");
			Text val = new Text();
			val.set(word[1]);
			output.collect(new Text(word[0]), val);
			
			
		}

	}
	
	/*public static class Reduce extends MapReduceBase implements	Reducer<Text, Text, Text, Text>
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
		
	}*/
	
	public static void main(String[] args) throws Exception
	{
		
		JobConf conf = new JobConf(NInputInputFormatDemo.class);
		conf.setJobName("KeyValueInputFormatDemo");
		
		
		conf.set("mapred.line.input.format.linespermap", "2"  );
		
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);

		conf.setMapperClass(Map.class);
		//conf.setReducerClass(Reduce.class);
		conf.setNumReduceTasks(0);
		conf.setInputFormat(NLineInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);

		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));

		JobClient.runJob(conf);
		
	}

}
