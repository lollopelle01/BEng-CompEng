<div id="menu">
	<ul id="tabs">
		<li <%= request.getRequestURI().contains("home") ? "style=\"background-color: #ff6347;\"" : ""%>>
			<a href="<%= request.getContextPath() %>/pages/home.jsp">Home</a>
		</li>
		<li <%= request.getRequestURI().contains("catalogue") ? "style=\"background-color: #6495ed;\"" : ""%>>
			<a href="<%= request.getContextPath() %>/pages/catalogue.jsp">Manage catalogue</a>
		</li>
		<li <%= request.getRequestURI().contains("cart") ? "style=\"background-color: #ffebcd;\"" : ""%>>
			<a href="<%= request.getContextPath() %>/pages/cart.jsp">Manage cart</a>
		</li>
		<li <%= request.getRequestURI().contains("checkout") ? "style=\"background-color: #94fb94;\"" : ""%>>
			<a href="<%= request.getContextPath() %>/pages/checkout.jsp">Checkout</a>
		</li>
	</ul>
</div>