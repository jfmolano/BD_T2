package uniandes.mapRed;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class WCMapper extends Mapper<LongWritable, Text, Text, Text> {
	
	@Override
	protected void map(LongWritable key, Text value,
			Context context)
			throws IOException, InterruptedException {
		String fecha_ini_str = "1993-07-01";
		String fecha_fin_str = "1993-07-30";
		String pais_busqueda = "-";
		String nombre_busqueda = "-";
		Date fecha_ini = null;
		Date fecha_fin = null;
		try{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			fecha_ini = df.parse(fecha_ini_str);
			fecha_fin = df.parse(fecha_fin_str);
		}catch(Exception e){
			
		}
		
		String val = value.toString();
		if(val.startsWith("P")){
			boolean cumple_fecha = false;
			boolean cumple_nombre = false;
			boolean cumple_pais = false;
			String nombre = "EMPTY_STR";
			String fecha = "EMPTY_STR";
			String pais = "EMPTY_STR";
			String[] arr = val.split(";");
			try{
				nombre = arr[1];
				fecha = arr[2];
				pais = arr[3];				
			}catch(Exception e){
				
			}
			if(fecha_ini_str.equals("-")){
				cumple_fecha = true;
			}else{
				try{
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date bDate = df.parse(fecha);
					if(bDate.before(fecha_fin) && bDate.after(fecha_ini)){
						cumple_fecha = true;
					}
				}catch(ArrayIndexOutOfBoundsException | ParseException e){
					
				}
			}
			
			if(nombre_busqueda.equals("-")){
				cumple_nombre = true;
			}else{
				if(nombre.contains(nombre_busqueda)){
					cumple_nombre = true;
				}
			}
			
			if(pais_busqueda.equals("-")){
				cumple_pais = true;
			}else{
				if(pais.contains(pais_busqueda)){
					cumple_pais = true;
				}
			}
			if(cumple_fecha && cumple_nombre && cumple_pais){
				context.write(new Text(nombre), new Text("P"+nombre+";"+fecha+";"+pais+";"));				
			}
		}else if(val.startsWith("LR")){
			String[] arr = val.split(";");
			try{
				String nombre = arr[1];
				context.write(new Text(nombre), new Text("LI"+arr[1]+";"+arr[2]));					
			}catch(ArrayIndexOutOfBoundsException e){
				
			}
		}else if(val.startsWith("LD")){
			String[] arr = val.split(";");
			try{
				String nombre = arr[1];
				context.write(new Text(nombre), new Text("LI"+arr[2]+";"+arr[3]));					
			}catch(ArrayIndexOutOfBoundsException e){
				
			}	
		}
	}
}



