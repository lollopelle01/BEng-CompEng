<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="./scripts/utils.js"></script>
    <script src="./scripts/jquery-1.12.3.min.js"></script>
    <script src="./scripts/home.js" defer></script>
    <title>HOME</title>
</head>
<body>

    <% // Memorizziamo tavolo per accessi successivi
        int numTavolo = session.getAttribute("numTavolo");
    %>
    Hai selezionato il tavolo n° <%= numTavolo%>

<% // Se il bar è aperto posso fare acquisti
if(getServletContext().getAttribute("chiusura_tavolo")!=true) { %>
    <h1> ACQUISTO DRINK </h1>
    <form action="service" method="post">
        
        <label> Nome drink <br>
            <input type="text" name="nome_drink" id="nome_drink"> <br>
        </label>
        <label> Costo drink <br>
            <input type="text" name="costo_drink" id="costo_drink"> <br>
        </label>
        
        <input type="text" name="numTavolo" id="numTavolo" style="display: none;" value="${numTavolo}"> <br>
        <input type="text" name="richiesta" id="richiesat" style="display: none;" value="utente"> <br>

        <button type="submit">
    </form>
<%}%>

<% // Se il bar è chiuso posso fare calcolo
if(getServletContext().getAttribute("chiusura_tavolo")==true) { %>
    <h1> CALCOLO ACQUISTI </h1>
    <form action="service" method="get">
        
        <select id="richiesta">
            <option value="prezzo_tavolo">Prezzo totale del tavolo</option>
            <option value='prezzo_personale'>Prezzo singolo</option>
        </select>

        <input type="text" name="numTavolo" id="numTavolo" style="display: none;" value="${numTavolo}"> <br>
        
        <button type="submit">
    </form>

    <div id="risp_calcolo_acquisti"> <%= session.getAttribute("risp_calcolo_acquisti")%> </div>
<%}%>

</body>
</html>