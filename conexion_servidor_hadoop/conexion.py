import paramiko


nombre_consulta = 'Balvin'
pais_consulta = '-'
fecha_ini_consulta = '-'
fecha_fin_consulta = '1987-01-01'
#ip = 'IPFALSA'
#usuario = 'USUARIO_FALSO'
#password_cluster = 'PASS FALSO'
#ruta = 'RUTA FALSA'
ssh = paramiko.SSHClient()
ssh.set_missing_host_key_policy(
    paramiko.AutoAddPolicy())
ssh.connect(ip, username=usuario,
    password=password_cluster)
stdin, stdout, stderr = ssh.exec_command('hadoop fs -rm -r /'+ruta+'/resultado_parametros/')
salida = stdout.readlines()
print salida
comando = 'hadoop jar busqueda_parametros.jar uniandes.reuters.job.WordCounter /'+ruta+'/arbol_N2_V3/* /'+ruta+'/resultado_parametros '+nombre_consulta+' '+pais_consulta+' '+fecha_ini_consulta+' '+fecha_fin_consulta
stdin, stdout, stderr = ssh.exec_command(comando)
salida = stdout.readlines()
print salida
stdin, stdout, stderr = ssh.exec_command('hadoop fs -cat /'+ruta+'/resultado_parametros/*')
out = stdout.readlines()
lista_personas = []
lista_relaciones = []
pesos_paises = ''
str_fortalezas = ""
for el in out:
	#print el
	if el.startswith( 'P' ):
		obj = {}
		arreglo = el.split(";")
		nombre = arreglo[1]
		fecha = arreglo[2]
		pais = arreglo[3]
		obj['nombre'] = nombre
		obj['fecha'] = fecha
		obj['pais'] = pais
		lista_personas.append(obj)
	elif el.startswith( 'R;' ):
		obj = {}
		arreglo = el.split(";")
		objeto_1 = arreglo[1]
		objeto_2 = arreglo[2]
		obj['p1'] = objeto_1
		obj['p2'] = objeto_2
		lista_relaciones.append(obj)
	elif el.startswith( 'R_' ):
		arreglo = el.split(";")
		objeto_1 = arreglo[1]
		objeto_2 = arreglo[2]
		objeto_3 = arreglo[3]
		#print objeto_1
		#print objeto_2
		#print objeto_3
		objeto_3 = objeto_3.replace("\t", "")
		str_fortalezas = str_fortalezas + "\n" + objeto_1 + " y " + objeto_2 + ", peso: " + objeto_3


print " "
print "Lista personas:"
print lista_personas
print " "
print "Lista relaciones:"
print lista_relaciones
print " "
print "Fortaleza:"
print str_fortalezas
