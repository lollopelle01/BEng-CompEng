<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./styles/home.css">
        <script src="./scripts/jquery-1.12.3.min.js"></script>
        <script src="./scripts/home.js" defer></script>
        <title>Homepage</title>
    </head>
    <body>
        <main>
        	<div id="navbar">
				<table style="width: 100%;">
					<tr>
						<td class="navbar">
							<h1>OPERAZIONI TESTO</h1>
						</td>
						
						<td class="navbar">
							<form action="login" method="post" class="form">
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
                <label for="fname">Inserisci un testo di max 5000 caratteri</label> <br>
                <textarea id="text"></textarea> <br>
                <input type="text" readonly id="counter">
            </div>
            
            <div id="ajax"></div>
        </main>
    </body>
</html>