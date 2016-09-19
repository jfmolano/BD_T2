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

@app.route('/api/dar_marca', methods=['POST'])
def dar_marca_post():
	if not request.json or not 'Id' in request.json:
		abort(400)
	marca = {
	'Id': request.json['Id'],
	'Dato': request.json.get('Dato', "")
	}
	return jsonify({'marca': marca}), 201

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
	app.run(host= '127.0.0.1')
