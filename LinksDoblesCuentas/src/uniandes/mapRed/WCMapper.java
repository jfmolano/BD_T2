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
			String [] arr_t = val.split("::");
			String a = arr_t[1];
			String b = arr_t[2];
			context.write(new Text(a), new Text("sem::"+b));
			context.write(new Text(a), new Text("join::"+b+"::"+a));
		}		
	}

