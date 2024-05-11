<%@page import="beans.Article"%>
<%@ page session="true"%>
<html>
   <head>
		
        <title>Homepage</title>
		<link type="text/css" href="./styles/home.css" rel="stylesheet"></link>
        <script src="./scripts/jquery-1.12.3.min.js"></script>
        <script src="./scripts/home.js" defer></script>
   </head>
   <body>
        <header>
            <form action="admin.jsp">
                <table>
                    <tr>
                        <td><h1>HOMEPAGE</h1></td>
                        <td>
                            <label for="username">Username</label> <br>
                            <input type="text" name="username" id="username" placeholder="username"> <br>
                            <label for="password">Password</label> <br>
                            <input type="password" name="password" id="password" placeholder="username"> <br>
                            <button>Accedi</button>
                        </td>
                    </tr>
                </table>
            </form>
            
        </header>
        
        <%
        	if(session.getAttribute("article") == null) {
        %>
        	<main id="start-main">
            <table>
                <tr>
                    <td><textarea name="article" id="article" cols="80" rows="25" readonly></textarea></td>
                    <td>
                        <table>
                            <tr>
                                <td><button class="request-btn" id="request-btn">Richiedi permesso di scrittura</button></td>
                                <td><button class="release-btn" id="release-btn" hidden>Rilascia permesso di scrittura</button></td>
                            </tr>
                            <tr>
                                <td>
                                    <textarea name="text" id="text" cols="30" rows="10" disabled></textarea> <br>
                                    <button class="append-btn" id="append-btn" disabled>Append</button>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </main>

        <div class="overlay" id="overlay">
            <div class="start-container" id="start-container">
                <h2>INSERIRE IL NOME DI UN FILE SUL SERVER</h2>
            <input class="fname" type="text" name="fname" id="fname" placeholder="Nome del file seguito da % (max 64 caratteri)">
            </div>
        </div>
        <%
        	} else {
        		Article article = (Article) session.getAttribute("article");
        %>
            <main id="article-main">
            <table>
                <tr>
                    <td><textarea name="article" id="article" cols="20" rows="25" value="<%=article.getText()%>" readonly></textarea></td>
                    <td>
                        <table>
                            <tr>
                                <td><button class="request-btn" id="request-btn">Richiedi permesso di scrittura</button></td>
                                <td><button class="release-btn" id="release-btn">Rilascia permesso di scrittura</button></td>
                            </tr>
                            <tr>
                                <td>
                                    <textarea name="text" id="text" cols="30" rows="10"></textarea> <br>
                                    <button class="append-btn" id="append-btn">Append</button>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </main>
		<%
        	}
		%>
   </body>
</html>