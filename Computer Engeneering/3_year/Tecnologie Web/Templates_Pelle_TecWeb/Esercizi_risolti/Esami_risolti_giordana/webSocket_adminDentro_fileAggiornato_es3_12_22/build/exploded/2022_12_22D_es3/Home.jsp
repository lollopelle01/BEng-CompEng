<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./styles/home.css">
        <script src="./scripts/jquery-1.12.3.min.js"></script>
        <title>Homepage</title>
        
        <script type="text/javascript">
		let ws
		let connesso = false
	
		document.addEventListener("DOMContentLoaded", function() {
			
			document.getElementById("iscriviti").addEventListener("click", function() {
				
				document.getElementById("iscriviti").disabled = true;
				
				ws = new WebSocket("ws://localhost:8080/2022_12_22D_es3/ws");
				
				ws.addEventListener("message", function(message) {
					document.getElementById("text").value += message.data + "\n"
				})
			})
		});
	
	</script>
	
    </head>
    <body>
        <main>
        	<div id="navbar">
				<table style="width: 100%;">
					<tr>
						<td class="navbar">
							<h1>Utente aggiornato</h1>
						</td>
						
						<td class="navbar">
							<form method="POST" action="Admin.jsp">
								<label for="username" class="label">Username</label>
								<input type="text" name="username" id="username"> <br>
								<label for="password" class="label">Password</label>
								<input type="password" name="password" id="password"> <br>
								<button id="login">Login</button>
							</form>
						</td>
					</tr>
				</table>
			</div>
			
            <div id="request-div">
            	<button id="iscriviti">ISCRIVITI AL SERVER</button><br>
            	
                <label for="text">Ecco il testo di risulttai.txt condiviso a tutti</label> <br>
                <textarea id="text" readonly></textarea> <br>
            </div>
            
            <div id="ajax"></div>
        </main>
    </body>
</html>