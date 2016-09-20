from flask import Flask, jsonify, abort, make_response, request
import csv
import paramiko

app = Flask(__name__)

marcas = [
    {
        'Id': u'1',
        'Dato': u'A'
    },
    {
        'Id': u'2',
        'Dato': u'B'
    }
]

@app.route('/api/marcas', methods=['GET'])
def get_marcas():
	return jsonify({'marcas': marcas})

@app.route('/dar_objeto_consulta', methods=['POST'])
def dar_objeto_consulta():
	if not request.json:
		abort(400)
	nombre_consulta = request.json.get('nombre', "")
	pais_consulta = request.json.get('pais', "")
	fecha_ini_consulta = request.json.get('fini', "")
	fecha_fin_consulta = request.json.get('ffin', "")
	ip = 'IP_FALSA'
	usuario = 'USUARIO_FALSO'
	password_cluster = 'PASS FALSO'
	ruta = 'RUTA FALSA'
	ssh = paramiko.SSHClient()
	ssh.set_missing_host_key_policy(
	    paramiko.AutoAddPolicy())
	ssh.connect(ip, username=usuario,
	    password=password_cluster)
	stdin, stdout, stderr = ssh.exec_command('hadoop fs -rm -r /'+ruta+'/resultado_parametros/')
	salida = stdout.readlines()
	print salida
	comando = 'hadoop jar busqueda_parametros.jar uniandes.reuters.job.WordCounter /'+ruta+'/arbol_N2_V3/* /'+ruta+'/resultado_parametros "'+nombre_consulta+'" "'+pais_consulta+'" '+fecha_ini_consulta+' '+fecha_fin_consulta
	print comando
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
		if el.startswith( 'P' ):
			arreglo = el.split(";")
			nombre = arreglo[1]
			fecha = arreglo[2]
			pais = arreglo[3]
			lista_personas.append({ "data": { "id": nombre , "name": nombre + ". " + fecha + ". " + pais } })
		elif el.startswith( 'R;' ):
			arreglo = el.split(";")
			objeto_1 = arreglo[1]
			objeto_2 = arreglo[2]
			lista_relaciones.append({ "data": { "source": objeto_1 , "target": objeto_2 } })
		elif el.startswith( 'R_' ):
			arreglo = el.split(";")
			objeto_1 = arreglo[1]
			objeto_2 = arreglo[2]
			objeto_3 = arreglo[3]
			objeto_3 = objeto_3.replace("\t", "")
			str_fortalezas = str_fortalezas + "\n" + objeto_1 + " y " + objeto_2 + ", peso: " + objeto_3

	objeto_retorno = {}
	objeto_retorno['personas'] = lista_personas
	objeto_retorno['relaciones'] = lista_relaciones
	objeto_retorno['fortaleza'] = str_fortalezas
	print " "
	print "Objeto de retorno:"
	print objeto_retorno
	return jsonify(objeto_retorno), 201

@app.route('/api/dar_marca/<Id>/<Dato>', methods=['GET'])
def dar_marca_get(Id,Dato):
	marca = {
	'Id': Id,
	'Dato': Dato
	}
	return jsonify({'marca': marca}), 201

@app.errorhandler(404)
def not_found(error):
    return make_response(jsonify({'error': 'Not found'}), 404)

if __name__ == '__main__':
	app.run(host= '0.0.0.0', port=8080)
