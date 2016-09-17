package uniandes.mapRed;

import java.io.IOException;
import java.util.HashMap;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WCMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	@Override
	protected void map(LongWritable key, Text value,
			Context context)
			throws IOException, InterruptedException {
		/*HashMap<String, Integer> palabrasLinea=new HashMap<String, Integer>();
		String[] palabras = value.toString().split("([().,!?:;'\"-]|\\s)+");
		for(String palabra:palabras){
			String lw=palabra.toLowerCase().trim();
			if(lw.equals("")){continue;} //No queremos contar espacios
			//Si la palabra existe en el hashmap incrementa en 1 su valor,
			//en caso contrario la agrega y le asigna 1.
			palabrasLinea.put(lw,
					palabrasLinea.containsKey(lw)?
							(palabrasLinea.get(lw)+1)
							:1);
		}*/
		//for(String k:palabrasLinea.keySet()){
		String val = value.toString();
		if(val.contains("Nairo Quintana")){
			context.write(new Text(val), new IntWritable(1));			
		}
		//}
		
	}
}
