package uniandes.mapRed;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WCMapper extends Mapper<LongWritable, Text, Text, Text> {
	
	@Override
	protected void map(LongWritable key, Text value,
			Context context)
			throws IOException, InterruptedException {
			String val = value.toString();
			String[] arreglo_str = val.split(";");
			if(arreglo_str[0].equals("P")){
				context.write(new Text(arreglo_str[1]), new Text("NOMBRE-REAL"));				
			}
			if(arreglo_str[0].equals("L")){
				context.write(new Text(arreglo_str[2]), new Text(arreglo_str[1]));				
			}
		}		
	}

