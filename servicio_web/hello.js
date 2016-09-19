$(document).ready(function() {
    $("#Consultar").click(function(){
        console.log("Consultar")
        var nombre = $('#txt_Nombre').val();
        var pais = $('#txt_Pais').val();
        var fini = $('#txt_FechaIni').val();
        var ffin = $('#txt_FechaFin').val();
        console.log("txt_Nombre " + nombre)
        console.log("txt_Pais " + pais)
        console.log("txt_FechaIni " + fini)
        console.log("txt_FechaFin " + ffin)
        url_post = "http://mine4102-3.virtual.uniandes.edu.co:8080/dar_objeto_consulta"
	    var jsonData = "{\"nombre\":\""+nombre+"\",\"pais\":\""+pais+"\",\"fini\":\""+fini+"\",\"ffin\":\""+ffin+"\"}";
        console.log(url_post)
        console.log("JSON: "+jsonData)
        $.ajax({
	type: "POST",
  	data: jsonData,
    	contentType: "application/json; charset=utf-8",
  	dataType: "json",
        url: url_post
        }).then(function(data) {
            console.log("data: " + data)
            console.log(data.fortaleza)
       $('#resultado_text').text(data.fortaleza);
    });
    });
});
