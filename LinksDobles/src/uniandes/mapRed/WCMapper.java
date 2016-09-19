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
			String [] arr_t = val.split("::");
			String per_1 = arr_t[1];
			String per_2 = arr_t[2];
			per_1 = per_1.replace("\t", "");
			per_2 = per_2.replace("\t", "");
			String new_str_1 = "LR::"+per_1+"::"+per_2+"::";
			String new_str_2 = "LR::"+per_2+"::"+per_1+"::";
			context.write(new Text(new_str_1), new IntWritable(1));
			context.write(new Text(new_str_2), new IntWritable(1));
		}		
	}

