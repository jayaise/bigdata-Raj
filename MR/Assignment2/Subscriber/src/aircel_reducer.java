import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;


public class aircel_reducer extends MapReduceBase implements Reducer<Text,DoubleWritable,Text,DoubleWritable>{
	public void reduce(Text key, Iterator<DoubleWritable> value,OutputCollector<Text,DoubleWritable> output, Reporter reporter) throws IOException{	
		double total_bytes=0;
		while (value.hasNext()) {
		
			// replace ValueType with the real type of your value
			total_bytes += value.next().get();
			// process value
		}
		output.collect(key,new DoubleWritable(total_bytes));
	}
}

