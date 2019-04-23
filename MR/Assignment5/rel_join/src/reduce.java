import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;


public class reduce extends MapReduceBase implements Reducer<DoubleWritable,Text,DoubleWritable,Text> {

	public void reduce (DoubleWritable key, Iterator<Text> values,OutputCollector<DoubleWritable,Text> output, Reporter reporter) throws IOException {
		
		ArrayList<String> arr = new ArrayList<String>();
		arr.clear();
		
		String line = null;
		
		String base=null;
		int counter = 0;
		
		int first=0;
		int i=0;
		
		while(values.hasNext())	{
			line=values.next().toString();
			StringTokenizer token = new StringTokenizer(line);
			if(token.countTokens()==3){
				token.nextToken();
				while (token.hasMoreTokens()){
					if(first==0){
						base=token.nextToken();
						first=1;
					}
					else
						base=base+'\t'+token.nextToken();
					}
				}
			else {
				arr.add(line);
				counter++;
			}
			
		}
		
		
		while(i<(counter)){
			line = base +" "+'\t'+arr.get(i);
			output.collect(key, new Text(line));
			i++;
		}
		}
	
}
