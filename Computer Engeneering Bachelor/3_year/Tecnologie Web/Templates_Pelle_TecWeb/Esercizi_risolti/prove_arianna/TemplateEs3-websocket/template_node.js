/******* TEMPLATE DI STRUTTURA *********************************************************************************/

// Dichiarazione dei moduli
const http = require('http'); // permette di creare il server
const fs = require('fs'); // permette di leggere/scrivere su file
const readline = require('readline'); // permette di leggere le righe linea x linea
const url = require('url'); // permette di estrarre url interos
const querystring = require('querystring'); // permette di analizzare nello specifico l'url

// Dichiarazione nodo
const hostname = '127.0.0.1'; //localhost
const port = 3000;

// Creiamo il server
const server = http.createServer((req, res) => { // req, res come in servlet
    res.statusCode = 200;
    res.setHeader('Content-Type', 'text/html');

    // Restituiamo una pagina html di solito
    let html = '<!DOCTYPE html>'+
    '<html>'+
    ' <head>'+
    ' <meta charset="utf-8" />'+
    ' <title>TITOLO</title>'+
    ' </head>'+
    ' <body>';

    // Logica applicativa per calcolare ciò che sta dentro alla pagina
    // ... vedi sotto ...

    // Chiudiamo l'html --> DA FARE SOLO ALLA FINE 
    html+=' </body>'+
    '</html>';

    // Restituiamo l'html
    res.write(html);
	res.end();

}); 

// Lanciamo il server in ascolto
server.listen(port, hostname, () => {
    console.log(`Server running at http://${hostname}:${port}/`);
});




/******* TEMPLATE LOGICA APPLICATIVA *********************************************************************************/

// === Modulo fs =====================================================================================
// Abbiamo 4 modalità di gestire I/O

// 1) Lettura normale - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
fs.readFile("myFile.txt", function(error, dataBuffer){

    // Controllare gli errori
    if (error) {console.log("errore nella lettura del File!!");	} 
    else {
        // logica da applicare a tutto il testo, contenuto in dataBuffer
        // ...
    }

});

// 2) Scrittura normale - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
fs.writeFile('myFile.txt', contenuto_da_scrivere, 'utf8', (error) => {
    if (error) {
      console.error(error);
      return;
    }
    
    console.log('Il file è stato scritto correttamente.');
});


// 3) Lettura in stream - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
const myFileReader = fs.createReadStream('file.txt', 'utf8');
myFileWriter.read(); // ATTENZIONE: leggere i soli dati presenti nello stream al momento

// 4) Scrittura in stream - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
const myFileWriter = fs.createWriteStream('new_'+fileName, {
    flags: 'a' // appen
});
myFileWriter.write("contenuto da scrivere");



// === Modulo readline ================================================================================
const rl = readline.createInterface({ // --> abilita funzione successiva 
    input: fs.createReadStream(filename),
    output: process.stdout,
    terminal: false
});

rl.on('line', function (line) { // --> ogni line è una linea del file
    // Logica da applicare riga x riga
    // . . .
});

rl.on('close', function(){
    // Logica da applicare a lettura conclusa
    // ...

    // Esempio utile --> scrittura dei dati raccolti in html
    html += '<label for="lines">DESCRIZIONE DEL DATO_RACCOLTO</label>'+
		'<input type="text" id="lines" value="'+DATO_RACCOLTO+'" readonly> </br>';
});



// === Modulo url =====================================================================================
// Esempio richiesta per capire meglio
req.url = "https://www.example.com/search?query=javascript&page=1"
const parsedUrl = url.parse(req.url)
    // Allora avremo:
    parsedUrl.protocol = 'https:'
    parsedUrl.host = 'www.example.com'
    parsedUrl.pathname = '/search'
    parsedUrl.query = { query: 'javascript', page: '1' }

    

/// === Modulo querystring =============================================================================
// Esempio richiesta per capire meglio
req.url = "https://www.example.com/search?query=javascript&page=1"
const params = querystring.parse(url.parse(req.url).query);
    // Allora avremo
    params['query'] = 'javascript'  /*oppure*/;     params.query = 'javascript'
    params['page'] = '1'            /*oppure*/;     params.page = '1'

// Se voglio scrivere una nuova query
const newQuery = {
    query: 'python',
    page: 2
};
const newQueryString = querystringModule.stringify(newQuery); // = 'query=python&page=2'



