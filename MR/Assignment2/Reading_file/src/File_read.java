import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;



public class File_read {
	
	public static void main(String args[]) throws IOException{
		
	//reading a file
		File file = new File("E:/Data_File.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String str,id,bytes_str;
		double bytes,V;
		
		
		
		
		HashMap<String, Double> hashmap = new HashMap<String, Double>();
		
		while ((str=br.readLine())!=null && str.length()!=0){
			
			
			id=str.substring(15, 26);
			bytes_str=str.substring(87,97);
			bytes= Double.parseDouble(bytes_str);
			V=0;
			
		if (hashmap.containsKey(id)){
		
			V=bytes+ Double.parseDouble(hashmap.get(id).toString());
		//	System.out.println(Double.parseDouble(hashmap.get(id).toString()));
			hashmap.remove(id);
					
			hashmap.put(id, V);
			
		}
		else{
			hashmap.put(id, bytes);
		}
			
			
		}
		
		br.close();
		File filo = new File("E:/file_out.txt");
		BufferedWriter bw=new BufferedWriter(new FileWriter(filo));
		
		Iterator<Entry<String, Double>> itr = hashmap.entrySet().iterator();
		while (itr.hasNext()){
					Entry<String, Double> mp = itr.next();
					str = (mp.getKey().toString());
					
					V =Double.parseDouble(mp.getValue().toString());
					
					bw.write(str+"__________"+String.format("%f", V));
					bw.newLine();
		}
		bw.close();
		
			
}}