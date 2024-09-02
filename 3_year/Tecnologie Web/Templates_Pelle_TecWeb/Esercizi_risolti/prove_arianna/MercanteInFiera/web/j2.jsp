<%@ page session="true"%>
<%@ page import="beans.Utente"%>
<%@ page import="beans.Gioco"%>
<%@ page import="java.time.*"%>

<%
Gioco gioco = (Gioco) application.getAttribute("gioco");
int cartaVincitrice = -1;
if(!gioco.isGiocoCominciato()){
	cartaVincitrice = gioco.getVincitore();

%>
<h1>La carta vincitrice e': <%= cartaVincitrice %></h1>
<%
} else {
%>
<h1>Il gioco non si e' concluso</h1>
<%
}
%>
