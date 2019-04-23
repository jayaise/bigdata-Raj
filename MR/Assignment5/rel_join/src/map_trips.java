import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;


public class map_trips extends MapReduceBase implements Mapper<LongWritable,Text,DoubleWritable,Text> {
	
	public void map(LongWritable key, Text value,  OutputCollector<DoubleWritable,Text> output,Reporter reporter) throws IOException {
		String line=value.toString();
		String outline2=null;
		int counter =0;
		double id=0;
		StringTokenizer token = new StringTokenizer(line);
		
		if ( token.hasMoreTokens()){
			id = Double.parseDouble(token.nextToken());
			
		}
		while (token.hasMoreTokens() ){
			if(counter == 1)
				outline2 = outline2 + ('\t'+token.nextToken());
			else{
				outline2 = token.nextToken();
				counter =1;
			}
		}
		output.collect(new DoubleWritable(id),new Text(outline2));
	}
}
