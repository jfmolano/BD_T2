from flask import Flask, jsonify, abort, make_response, request
import csv

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

@app.route('/api/suma', methods=['POST'])
def dar_suma_post():
	if not request.json or not 'A' in request.json:
		abort(400)
	A = int(request.json['A'])
	B = int(request.json.get('B', ""))
	marca = {
	'Resultado': (A+B)
	}
	return jsonify({'marca': marca}), 201

@app.route('/api/resta/<A>/<B>', methods=['GET'])
def dar_marca_get(A,B):
	marca = {
	'Resultado': (int(A)-int(B))
	}
	return jsonify({'marca': marca}), 201

@app.errorhandler(404)
def not_found(error):
    return make_response(jsonify({'error': 'Not found'}), 404)

if __name__ == '__main__':
	app.run(host= '127.0.0.1')
