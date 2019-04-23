import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;


public class sortreducer extends MapReduceBase implements Reducer<DoubleWritable,Text,DoubleWritable,Text> {

	public void reduce(DoubleWritable key, Iterator<Text> value,OutputCollector<DoubleWritable,Text> output, Reporter reporter) throws IOException {
		
	
		output.collect(key,value.next());
	}

}

