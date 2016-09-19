package uniandes.mapRed;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WCMapper extends Mapper<LongWritable, Text, Text, Text> {
	private static final Log LOG = LogFactory.getLog(WCMapper.class);
	
	@Override
	protected void map(LongWritable key, Text value,
			Context context)
			throws IOException, InterruptedException {
			String val = value.toString();
			if(val.startsWith("P;")){
				String[] arreglo_str = val.split(";");
				context.write(new Text(arreglo_str[1]), new Text("NOMBRE-REAL::"+arreglo_str[1]+";"+arreglo_str[2]+";"+arreglo_str[3]+"::"));				
			}
			else{
				String[] arreglo_str = val.split("::");
				try{
				context.write(new Text(arreglo_str[2].replace("\t","")), new Text(arreglo_str[1].replace("\t","")));	
				} catch (Exception e){
					LOG.info("+ + + + + + + VALOR: + + + + + + + : "+val);					
				}
			}
		}		
	}

