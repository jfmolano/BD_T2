package uniandes.mapRed;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WCReducer extends Reducer<Text, Text, Text, Text> {
	@Override
	protected void reduce(Text key, Iterable<Text> values,
			Context context)
			throws IOException, InterruptedException {
		ArrayList<String> arreglo_str = new ArrayList<String>();
		boolean escribir = false;
		String info = "";
		for(Text iw:values){
			String str_act = iw.toString();
			if(str_act.startsWith("NOMBRE-REAL")){
				escribir = true;
				info = str_act.split("::")[1];
			}
			else{
				arreglo_str.add(str_act);
			}
		}
		if(escribir){
			for(int i=0;i<arreglo_str.size();i++){
				context.write(new Text("LR::"+info), new Text("::"+arreglo_str.get(i)));				
			}		
		}		
	}

}
