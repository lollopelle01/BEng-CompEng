<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="beans.*"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="./scripts/utils.js"></script>
    <script src="./scripts/jquery-1.12.3.min.js"></script>
    <script src="./scripts/home.js" defer></script>
    <title>Document</title>
</head>
<body>

	<%	// Tokenizziamo il file --> compiliamo lista di hotel
		List<Hotel> hotels = new ArrayList<Hotel>();
		List<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();
		BufferedReader br = new BufferedReader(new FileReader("hotel.txt"));
		String line;
		String[] argomenti = new String[3];
		Hotel hotel = new Hotel();
		
		while((line=br.readLine()) != null){
			argomenti = line.split(",");
			
			hotel.setId(Integer.valueOf(argomenti[0]));
			hotel.setCamere_totali(Integer.valueOf(argomenti[1]));
			hotel.setPrezzo_statico_camere(Float.valueOf(argomenti[2]));
			hotel.setCamere_disponibili(hotel.getCamere_totali());
			
			hotels.add(hotel);
		}
		br.close();
		
		this.ServletContext().setAttribute("num_hotel", hotels.size());
		this.ServletContext().setAttribute("lista_hotel", hotels);
		this.ServletContext().setAttribute("lista_prenotazioni", prenotazioni);
		this.ServletContext().setAttribute("lista_prenotazioni_ufficiali", new ArrayList<Prenotazione>());
	%>

    <h1>PRENOTAZIONE CAMERA</h1>
    
    <div id="numero_hotel" style="display: none";><%= application.getAttribute("num_hotel") %></div>
    
        ID_albergo: <input type="text" name="id_albergo" id="id_albergo"> <br>
        Check-in: <input type="text" name="check_in" id="check_in"> <br>
        Check-out: <input type="text" name="check_out" id="check_out"> <br>
    
    <div id="ajax_message"></div>
    
    <div id="risposta" style="display: none;">
    	<form action="service" method="post">
    		Hotel: <input id="id_risp"/>
	    	Check-in: <input id="checkin_risp"/>
	    	Check-out: <input id="checkout_risp"/>
	    	Prezzo: <input id="prezzo_finale"/>
	    	<input type="submit" value="acquista">
    	</form>
    </div>
    
</body>
</html>