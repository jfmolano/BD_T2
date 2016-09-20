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
       $('#cy').cytoscape({
      style: cytoscape.stylesheet()
        .selector('node')
          .css({
            'content': 'data(name)',
            'text-valign': 'center',
            'color': 'white',
            'text-outline-width': 2,
            'text-outline-color': '#888'
          })
        .selector('edge')
          .css({
            'target-arrow-shape': 'triangle'
          })
        .selector(':selected')
          .css({
            'background-color': 'black',
            'line-color': 'black',
            'target-arrow-color': 'black',
            'source-arrow-color': 'black'
          })
        .selector('.faded')
          .css({
            'opacity': 0.25,
            'text-opacity': 0
          }),
      
      elements: {
        nodes: [
          { data: { id: 'j', name: 'Jerry' } },
          { data: { id: 'e', name: 'Elaine' } },
          { data: { id: 'k', name: 'Kramer' } },
          { data: { id: 'g', name: 'George' } }
        ],
        edges: [
          { data: { source: 'j', target: 'e' } },
          { data: { source: 'j', target: 'k' } },
          { data: { source: 'j', target: 'g' } },
          { data: { source: 'e', target: 'j' } },
          { data: { source: 'g', target: 'j' } }
        ]
      },
      
      ready: function(){
        window.cy = this;
        
        // giddy up...
        
        cy.elements().unselectify();
        
        cy.on('tap', 'node', function(e){
          var node = e.cyTarget; 
          var neighborhood = node.neighborhood().add(node);
          
          cy.elements().addClass('faded');
          neighborhood.removeClass('faded');
        });
        
        cy.on('tap', function(e){
          if( e.cyTarget === cy ){
            cy.elements().removeClass('faded');
          }
        });
      }
    });
       
    });
    });
});


