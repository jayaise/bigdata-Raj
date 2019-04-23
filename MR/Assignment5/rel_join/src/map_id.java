import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;


public class map_id extends MapReduceBase implements Mapper<LongWritable,Text,DoubleWritable,Text> {
	private final String id_file = "zzzzzzzzzz";
	public void map(LongWritable key, Text value,  OutputCollector<DoubleWritable,Text> output,Reporter reporter) throws IOException {
		String line=value.toString();
		String outline=id_file;
		double id=0;
		StringTokenizer token = new StringTokenizer(line);
		if ( token.hasMoreTokens()){
			id = Double.parseDouble(token.nextToken());
		}
		while (token.hasMoreTokens() ){
			outline = outline + '\t' +token.nextToken();
		}
		output.collect(new DoubleWritable(id),new Text(outline));
	}
}
