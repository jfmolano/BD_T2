$(document).ready(function() {
    $("#Resta").click(function(){
        var A = $('#txt_A').val();
        var B = $('#txt_B').val();
        console.log("A " + A)
        console.log("B " + B)
        url_get = "http://localhost:5000/api/resta/" + A + "/" + B
        console.log(url_get)
        $.ajax({
	type: "GET",
        url: url_get
        }).then(function(data) {
            console.log("data: " + data)
	    console.log("data.marca " + data.marca)
	    console.log("data.marca.Resultado " + data.marca.Resultado)
       $('#resultado_text').text(data.marca.Resultado);
    });
    });

    $("#Suma").click(function(){
        console.log("Suma")
        var A = $('#txt_A').val();
        var B = $('#txt_B').val();
        console.log("A " + A)
        console.log("B " + B)
        url_post = "http://localhost:5000/api/suma"
	var jsonData = "{\"A\":\""+A+"\",\"B\":\""+B+"\"}";
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
	    console.log("data.marca " + data.marca)
	    console.log("data.marca.Resultado " + data.marca.Resultado)
       $('#resultado_text').text(data.marca.Resultado);
    });
    });
});
