import paramiko

ssh = paramiko.SSHClient()
ssh.set_missing_host_key_policy(
    paramiko.AutoAddPolicy())
ssh.connect('IP_FALSA', username='USUARIO_FALSO',
    password='PASSWORD_FALSO')
stdin, stdout, stderr = ssh.exec_command('hadoop jar busqueda_parametros.jar uniandes.reuters.job.WordCounter /RUTA_FALSA/arbol_N2_V3/* /RUTA_FALSA/resultado_parametros Balvin Colombia - 1960-01-01')
stdout.readlines()