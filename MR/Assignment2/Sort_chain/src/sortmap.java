import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;


public class sortmap extends MapReduceBase implements Mapper<Text,DoubleWritable,DoubleWritable,Text> {
	
	public void map(Text key, DoubleWritable value,OutputCollector<DoubleWritable,Text> output, Reporter reporter) throws IOException {
		output.collect(value,key);
	}

}
