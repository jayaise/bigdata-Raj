import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;


public class bat_red extends Reducer<Text,Text,NullWritable,Text>{
	public void reduce(Text key,Iterable<Text> values,Context context){
		NullWritable none = NullWritable.get();
		MultipleOutputs<NullWritable,Text> m = new MultipleOutputs<NullWritable,Text>(context);
		
		for(Text t:values){
			try {
				m.write(none, t, key.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			m.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
