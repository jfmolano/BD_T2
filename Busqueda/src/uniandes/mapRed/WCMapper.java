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

public class WCMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	@Override
	protected void map(LongWritable key, Text value,
			Context context)
			throws IOException, InterruptedException {
		String fecha_ini_str = context.getConfiguration().get("fini_consulta");
		String fecha_fin_str = context.getConfiguration().get("ffin_consulta");
		String pais_busqueda = context.getConfiguration().get("pais_consulta");
		String nombre_busqueda = context.getConfiguration().get("nombre_consulta");		
		
		String val = value.toString();
		if(val.startsWith("P")){
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
			boolean validacion = corresponde(nombre,pais,fecha,nombre_busqueda,pais_busqueda,fecha_ini_str,fecha_fin_str);
			if(validacion){
				context.write(new Text("P;"+nombre+";"+fecha+";"+pais+";"), new IntWritable(1));
			}			
		}else if(val.startsWith("LR")){
			String[] arr = val.split("::");
			String elemento_1 = arr[1];
			String elemento_2 = arr[2];
			String[] arr2 = elemento_1.split(";");
			String nombre = "EMPTY_STR";
			String fecha = "EMPTY_STR";
			String pais = "EMPTY_STR";
			try{
				nombre = arr2[0];
				fecha = arr2[1];
				pais = arr2[2];				
			}catch(Exception e){
				
			}
			boolean validacion = corresponde(nombre,pais,fecha,nombre_busqueda,pais_busqueda,fecha_ini_str,fecha_fin_str);
			if(validacion){
				String[] arr3 = elemento_2.split(";");
				String nombre_2 = "EMPTY_STR";
				String fecha_2 = "EMPTY_STR";
				String pais_2 = "EMPTY_STR";
				try{
					nombre_2 = arr3[0];
					fecha_2 = arr3[1];
					pais_2 = arr3[2];				
				}catch(Exception e){
					
				}
				context.write(new Text("P;"+nombre_2+";"+fecha_2+";"+pais_2+";"), new IntWritable(1));
				context.write(new Text("R;"+nombre+";"+nombre_2+";"), new IntWritable(1));
				context.write(new Text("R_PAIS;"+pais+";"+pais_2+";"), new IntWritable(1));
				context.write(new Text("R_PAIS;"+pais_2+";"+pais+";"), new IntWritable(1));
			}
			
		}else if(val.startsWith("LD")){
			String[] arr = val.split("::");
			String elemento_1 = arr[1];
			String elemento_2 = arr[2];
			String elemento_3 = arr[3];
			String[] arr2 = elemento_1.split(";");
			String nombre = "EMPTY_STR";
			String fecha = "EMPTY_STR";
			String pais = "EMPTY_STR";
			try{
				nombre = arr2[0];
				fecha = arr2[1];
				pais = arr2[2];				
			}catch(Exception e){
				
			}
			boolean validacion = corresponde(nombre,pais,fecha,nombre_busqueda,pais_busqueda,fecha_ini_str,fecha_fin_str);
			if(validacion){
				String[] arr3 = elemento_2.split(";");
				String nombre_2 = "EMPTY_STR";
				String fecha_2 = "EMPTY_STR";
				String pais_2 = "EMPTY_STR";
				try{
					nombre_2 = arr3[0];
					fecha_2 = arr3[1];
					pais_2 = arr3[2];				
				}catch(Exception e){
					
				}
				
				String[] arr4 = elemento_3.split(";");
				String nombre_3 = "EMPTY_STR";
				String fecha_3 = "EMPTY_STR";
				String pais_3 = "EMPTY_STR";
				try{
					nombre_3 = arr4[0];
					fecha_3 = arr4[1];
					pais_3 = arr4[2];				
				}catch(Exception e){
					
				}
				context.write(new Text("P;"+nombre_3+";"+fecha_3+";"+pais_3+";"), new IntWritable(1));
				context.write(new Text("R;"+nombre_2+";"+nombre_3+";"), new IntWritable(1));
				context.write(new Text("R_PAIS;"+pais_2+";"+pais_3+";"), new IntWritable(1));
				context.write(new Text("R_PAIS;"+pais_3+";"+pais_2+";"), new IntWritable(1));
			}
		}
	}
	
	private boolean corresponde(String nombre,String pais,String fecha,String nombre_busqueda,String pais_busqueda,String fecha_ini_str,String fecha_fin_str){

		boolean cumple_fecha = false;
		boolean cumple_nombre = false;
		boolean cumple_pais = false;
		Date fecha_ini = null;
		Date fecha_fin = null;
		try{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			fecha_ini = df.parse(fecha_ini_str);
			fecha_fin = df.parse(fecha_fin_str);
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
		return cumple_fecha && cumple_nombre && cumple_pais;
	}
}



