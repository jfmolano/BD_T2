package uniandes.reuters.job;

import java.io.IOException;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import uniandes.mapRed.WCMapper;
import uniandes.mapRed.WCReducer;

public class WordCounter {
	public static void main(String[] args)  {
		if(args.length<6){
			System.out.println("Se necesitan las carpetas de entrada y salida");
			System.exit(-1);
		}
		String entrada = args[0]; //carpeta de entrada
		String salida = args[1];//La carpeta de salida no puede existir
		String nombre = args[2]; //Nombre
		String pais = args[3];//Pais
		String fecha_ini = args[4]; //Fecha ini
		String fecha_fin = args[5];//Fecha fin
		
		try {
			ejecutarJob(entrada, salida, nombre, pais, fecha_ini, fecha_fin);
		} catch (Exception e) { //Puede ser IOException, ClassNotFoundException o InterruptedException
			e.printStackTrace();
		} 
		
	}
	public static void ejecutarJob(String entrada, String salida, String nombre, String pais, String fini, String ffin) throws IOException,ClassNotFoundException, InterruptedException
	{
		/**
		 * Objeto de configuraci�n, dependiendo de la versi�n de Hadoop 
		 * uno u otro es requerido. 
		 * */
		Configuration conf = new Configuration();
		//conf.set("textinputformat.record.delimiter","</page>");
		conf.set("nombre_consulta",nombre);
		conf.set("pais_consulta",pais);
		conf.set("fini_consulta",fini);
		conf.set("ffin_consulta",ffin);
		Job wcJob=Job.getInstance(conf, "WordCounter Job");
		wcJob.setJarByClass(WordCounter.class);
		//////////////////////
		//Mapper
		//////////////////////
		
		wcJob.setMapperClass(WCMapper.class);
		
		wcJob.setMapOutputKeyClass(Text.class);
		wcJob.setMapOutputValueClass(IntWritable.class);
		///////////////////////////
		//Reducer
		///////////////////////////
		wcJob.setReducerClass(WCReducer.class);
		wcJob.setOutputKeyClass(Text.class);
		wcJob.setOutputValueClass(IntWritable.class);
		
		///////////////////////////
		//Input Format
		///////////////////////////
		//Advertencia: Hay dos clases con el mismo nombre, 
		//pero no son equivalentes. 
		//Se usa, en este caso, org.apache.hadoop.mapreduce.lib.input.TextInputFormat
		TextInputFormat.setInputPaths(wcJob, new Path(entrada));
		wcJob.setInputFormatClass(TextInputFormat.class); 
		
		////////////////////
		///Output Format
		//////////////////////
		TextOutputFormat.setOutputPath(wcJob, new Path(salida));
		wcJob.setOutputFormatClass(TextOutputFormat.class);
		wcJob.waitForCompletion(true);
		System.out.println(wcJob.toString());
	}
}
