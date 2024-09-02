<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ page import="Beans.Prenotazione"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page</title>
<% List<Prenotazione> pre = (List<Prenotazione>)this.getServletContext().getAttribute("preFin"); %>
</head>
<body>
<%
	for(Prenotazione p : pre)
	{
		%>
		<div>
		Codice Hotel: <%=p.getCodiceHotel() %><br>
		CheckIn: <%=p.getCheckIn() %><br>
		CheckOut: <%=p.getCodiceHotel() %><br>
		Prezzo: <%=p.getPrezzo() %><br><br>
		</div>
		<%
	}
%>



</body>
</html>