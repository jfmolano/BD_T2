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
		/*HashMap<String, Integer> palabrasLinea=new HashMap<String, Integer>();
		String[] palabras = value.toString().split("([().,!?:;'\"-]|\\s)+");
		for(String palabra:palabras){
			String lw=palabra.toLowerCase().trim();
			if(lw.equals("")){continue;} //No queremos contar espacios
			//Si la palabra existe en el hashmap incrementa en 1 su valor,
			//en caso contrario la agrega y le asigna 1.
			palabrasLinea.put(lw,
					palabrasLinea.containsKey(lw)?
							(palabrasLinea.get(lw)+1)
							:1);
		}*/
		//for(String k:palabrasLinea.keySet()){
	    // Now create matcher object.
		String val = value.toString();	    
		if(val.contains("Radamel Falcao")){
			String pattern_infobox = "\\{\\{Infobox[\\s\\S]*?\\}\\}";
		    Pattern p_infobox = Pattern.compile(pattern_infobox, Pattern.MULTILINE);
			Matcher m_infobox = p_infobox.matcher(value.toString());
		    String info_box = "";
		    if (m_infobox.find()) {
		    	info_box = m_infobox.group(0);
				String pattern_dob = "birth_date[\\s\\S]*?\\}\\}";
			    Pattern p_dob = Pattern.compile(pattern_dob, Pattern.MULTILINE);
		    	Matcher m_dob = p_dob.matcher(info_box.toString());
			    String dob = "";
			    if (m_dob.find()) {
			    	dob = m_dob.group(0);
			    	//Parsear fecha
			    	String pattern_ymd = "\\d{4}|\\d{3}|\\d{2}|\\d{1}";
				    Pattern p_ymd = Pattern.compile(pattern_ymd, Pattern.MULTILINE);
			    	Matcher m_ymd = p_ymd.matcher(dob);
				    int i = 0;
				    String[] num_arr = new String[3];
				    while (m_ymd.find() && i<3) {
				    	num_arr[i] = m_ymd.group(0);
				    	i++;
				    }
				    try{
				    	String anio = num_arr[0];
					    String mes = num_arr[1].length()==1?"0"+num_arr[1]:num_arr[1];
					    String dia = num_arr[2].length()==1?"0"+num_arr[2]:num_arr[2];
					    String fecha = anio + "-" + mes + "-" + dia;
				    	//Titulo
				    	String pattern_title = "<title>[\\s\\S]*?<\\/title>";
					    Pattern p_title = Pattern.compile(pattern_title, Pattern.MULTILINE);
				    	Matcher m_title = p_title.matcher(val.toString());
					    String titulo = "";
					    if (m_title.find()) {
					    	titulo = m_title.group(0);
							titulo = titulo.substring(7, titulo.length()-8);
							context.write(new Text(titulo+";"+fecha), new IntWritable(1));
					    }
				    }
				    catch(Exception e){
				    	
				    }
				    
			    }
		    }			
		}
		//}
		
	}
}
