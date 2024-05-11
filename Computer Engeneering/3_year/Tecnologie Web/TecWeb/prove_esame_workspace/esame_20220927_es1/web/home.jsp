<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="./scripts/utils.js"></script>
    <script src="./scripts/jquery-1.12.3.min.js"></script>
    <title>HOME</title>
</head>
<body>

	<% // Inizializziamo le variabili di sessione
		request.getSession().setAttribute("msg", "Benvenuto");
		request.getSession().setAttribute("permesso", false);
	%>

    <script>

        function myFunction(){
            var testo = $("#nome_articolo").val(); console.log("testo: " + testo);
            console.log(testo[testo.length - 1]);
            if(testo[testo.length - 1] == '%'){
            	$("#nome_articolo").val(testo.substring(0, testo.length - 1));
            	$("#myButton").click();
            }
        }

        $("#responseIframe").onLoad({
            function() {
                var contenuto = thiscontentDocument.body.innerText;
                $("#testo").val(contenuto);
            }
        });

    </script>

    <h1>ARTICOLO SCIENTIFICO</h1>

    <div id="msgs"> <%= request.getSession().getAttribute("msg")%> </div>
    
    <form id="formGet" action="service" method="get" target="responseIframe">
        <p>Inserisci il nome dell'articolo</p>
        <input type="text" id="nome_articolo" name="nome_articolo" maxlength="200" oninput="myFunction()"/>
        <button type="submit" id="myButton" style="display: none;"></button>
    </form>

    <br/>

    <form action="service" method="post">
        <p>Testo dell'articolo:</p>
        <textarea id="testo" name="testo"></textarea>  
        <button type="submit" id="permesso">Richiedi permesso di scrittura</button>
        <% if((boolean)request.getSession().getAttribute("permesso")==true) {%>
            <button type="submit" id="invia_modifica"></button>
        <%}%>
    </form>


</body>
</html>