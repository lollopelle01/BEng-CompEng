<!-- STRUTTURA PAGINA HTML -->
<%@ page session="true"%>

<%@ page errorPage="../errors/failure.jsp"%>

<!-- import di classi Java -->

<%@ page import="java.util.*"%>
<%@ page import="Beans.Articolo"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <!-- per non avere problema di cache -> aggiungo nell'header delle pagine HTML -->
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta http-equiv="Expires" content="-1"/>
	
		<title> Pagina Admin </title>
	
		</head>
    <body>
    
    	<%
    	
    		List<Articolo> articoli = (List<Articolo>) this.getServletContext().getAttribute("articoli");
    	
    		if (request.getParameter("revoca") != null && request.getParameter("revoca").equals("Revoca diritto scrittura")) {
    			
    			String articolo = (String) request.getParameter("articolo");
    			
    			Articolo currentArticle = null;
    			
    			for(Articolo art : articoli) {
    				if(art.getNome().equals(articolo)) {
    					currentArticle = art;
    					break;
    				}
    			}
    			
    			currentArticle.setAccesso(false);	
			} 		
    	%>
    
    
    	<h3>Benvenuto admin! Questi sono tutti gli articoli creati.</h3>
    	
    	<%
    		for(Articolo art : articoli) {	
    	%>
    		<form>
	    		<table>
	    			<tr>
	    				<td><label for="nome">Nome articolo:</label></td>
	    				<td><input type="text" name="articolo" value="<%=art.getNome()%>" readonly/></td>
	    			</tr>
	    			<tr>
	    				<td><label for="utenti">Utenti:</label></td>
	    				<td>
		    				<% for(String utente : art.getUsers()) {
		    				
		    				%>
		    					<input type="text" name="utente" value="<%=utente%>" readonly/><br>
		    				<%	
		    				}
		    				%>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td><input type="submit" name="revoca" value="Revoca diritto scrittura"/></td>
	    			</tr>
	    		</table>
			</form>
			<br>
			<hr>
			<br>
    	<%		
    		}
    	%>

    </body>
</html>