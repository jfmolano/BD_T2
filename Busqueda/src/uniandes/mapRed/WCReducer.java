package uniandes.mapRed;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WCReducer extends Reducer<Text, Text, Text, IntWritable> {
	@Override
	protected void reduce(Text key, Iterable<Text> values,
			Context context)
			throws IOException, InterruptedException {
		ArrayList<String> links = new ArrayList<String>();
		String respuesta = "";
		boolean escribir = false;
		for(Text iw:values){
			String val = iw.toString();
			String[] val_arr = val.split(";");
			if(val_arr[0].equals("P")){
				escribir = true;
				respuesta = val;
			}else{
				links.add(val);
			}
		}
		if(escribir){
			for(int i=0;i<links.size();i++){
				respuesta = respuesta + links.get(i);
			}
			context.write(new Text(respuesta), new IntWritable(1));
		}
	}

}