<%-- uso della sessione --%>
<%@ page session="true"%>

<%-- info pagina --%>
<%@ page info="Pagina jsp"%>
<%@ page language="java" import="java.net.*"%>

<%-- import java --%>
<%@ page import="beans.Utente"%>
<%@ page import="beans.Asta"%>

<%-- pagina per la gestione di errori --%>
<%@ page errorPage="errors/failure.jsp"%>

<%-- 
	/*oggetti built in*/
	//page --> info di una servlet
	//config --> getInitParamNames
	//request
	//response
	//out
	//SESSION --> getAttribute setAttribute
	//application (servletContext) --> var globali di application

	<jsp:forward page="url-dest">
	<jsp:param name="par1" value="val1"/>
	<jsp:param name="par2" value="val2"/>
	</jsp:forward>
	
	<jsp:include page="url-dest" flush="true">
	<jsp:param name="par1" value="val1"/>
	<jsp:param name="par2" value="val2"/>
	</jsp:include>
	
	<jsp:useBean id="nomeLogico" class="JavaBeans.aBean" scope="page|request|session|application"/>
	<jsp:getProperty property="nomeProp" name="nomeLogico"/>
	<jsp:setProperty property="nomeProp" name="nomeLogico" value="val"/>
	
 --%>

<jsp:useBean id="astamanager" class="beans.AstaManager"
	scope="application" />


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Example jsp</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/styles/default.css"
	type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/asta.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/utils.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/json.js"></script>
</head>
<body onload="riceviAsta(myGetElementById('asta'))">
<%
	Utente utente = (Utente) session.getAttribute("utente");
	if (utente == null) {
		request.setAttribute("loginerror", "Non sei autenticato!");
		%>
		<jsp:forward page="/accessoServlet"/>
		<%
	}
	if (!astamanager.hasUtente(utente)) {
		request.setAttribute("loginerror", "Non sei autorizzato!");
		%>
		<jsp:forward page="/accessoServlet"/>
		<%
	}
	
	Asta lastAsta = astamanager.getLastAsta();
	if (lastAsta != null && lastAsta.getBestBidder().equals(utente)) {
		%>
		<h3>Complimenti, ti sei aggiudicato l'ultima asta!</h3>
		<a href="./pagamento.jsp">Paga ora!</a>
		<br/>
		<%
	}
%>
	<div id="asta"></div>
	<br/>
	<fieldset>
		<legend>Fai un'offerta</legend>
		<form action="" onsubmit="return submitOffer(this, myGetElementById('asta'))">
			<input name="offerta" id="offerta" type="number" min="0" value="1" step="any">
			<input type="submit" value="Offri!">
		</form>
	</fieldset>
	
	
</body>
</html>
