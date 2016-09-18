package uniandes.mapRed;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WCMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	@Override
	protected void map(LongWritable key, Text value,
			Context context)
			throws IOException, InterruptedException {
			String val = value.toString();		
			String link1 = val.replace("\t", "")+";";
			String [] arr_t = link1.split(";");
			String new_str = "LR;"+arr_t[2]+";"+arr_t[1]+";";
			context.write(new Text(link1), new IntWritable(1));
			context.write(new Text(new_str), new IntWritable(1));
		}		
	}

