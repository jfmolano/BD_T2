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
		ArrayList<String> semillas = new ArrayList<String>();
		ArrayList<String> joins = new ArrayList<String>();
		for(Text iw:values){
			String val = iw.toString();
			String[] val_arr = val.split(";");
			if(val_arr[0].equals("sem")){
				semillas.add(val_arr[1]);
			}else{
				joins.add(val_arr[1]+";"+val_arr[2]);
			}
		}
		for(int i = 0;i<joins.size();i++){
			String join_str = joins.get(i);
			for(int j = 0;j<semillas.size();j++){
				String semilla_str = semillas.get(j);
				if(!join_str.contains(semilla_str)){
					context.write(new Text("LD;"+join_str+";"+semilla_str+";"), new IntWritable(0));					
				}
			}
		}
	}

}
