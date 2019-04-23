import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;


public class aircel_mapper extends MapReduceBase implements Mapper<LongWritable,Text,Text,DoubleWritable> {
	public static final double MISSING=0;
	public void map(LongWritable key, Text value,OutputCollector<Text,DoubleWritable> output, Reporter reporter) throws IOException {
		String line = value.toString();
		String subID="Dummy",subId;
	
	
		if(line.isEmpty()){
			output.collect(new Text (subID), new DoubleWritable(1));
		}
		else{
			subId = line.substring(15,26);
			Double bytes = Double.parseDouble(line.substring(87,97));
			
			
			if(bytes==null){
				bytes=MISSING;
			}
			output.collect(new Text(subId), new DoubleWritable(bytes));
		}
		
	}

}
