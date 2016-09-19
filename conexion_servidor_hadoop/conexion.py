import paramiko


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
stdin, stdout, stderr = ssh.exec_command('hadoop jar busqueda_parametros.jar uniandes.reuters.job.WordCounter /'+ruta+'/arbol_N2_V3/* /'+ruta+'/resultado_parametros Balvin Colombia - 1960-01-01')
salida = stdout.readlines()
print salida
stdin, stdout, stderr = ssh.exec_command('hadoop fs -cat /'+ruta+'/resultado_parametros/*')
out = stdout.readlines()
for el in out:
	print el
#print out
