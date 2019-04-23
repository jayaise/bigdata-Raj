import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class bat_map extends Mapper<LongWritable,Text,Text,Text>{
	public void map(LongWritable key,Text value,Context context){
		String line = value.toString();
		int i = 1;
		String tab = null;
		String keyy=null;
		StringTokenizer token = new StringTokenizer(line,",");
		tab = token.nextToken();
		while(token.hasMoreElements()){
			if(i != 2){
				tab = tab + "," + token.nextToken();
				i=i+1;
			}
			else{
				keyy = token.nextToken();
				i=i+1;
			}
		}
		try {
			context.write(new Text(keyy), new Text(tab));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
