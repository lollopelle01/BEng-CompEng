const http = require('http');
const fs = require('fs');
const readline = require('readline');
const hostname = '127.0.0.1';
const port = 3000;
const url = require('url');
let oldNumOfLines = -1;

var reqUrl = 'http://127.0.0.1:3000/page';

const server = http.createServer((req, res) => {
    let numOfLines = 0;
    let numOfWordsPerLine = [];
    let occorrenze = 0;
    let line = -1;
    res.statusCode = 200;
    let newLines = [];
    let newFile = [];
    let oldLines = [];
    res.setHeader('Content-Type', 'text/html');
    res.write('<h1>Esercizio 3</h3>');
    res.write("<p>Scrivere URL per ricevere notizie nuove, ad esempio:</p>");
    res.write("<p>http://127.0.0.1:3000/news.txt?letter=N</p>");
    res.write("<p>Necessario il refresh della pagina</p>")

    //http://127.0.0.1:3000/hello.txt
    const urlObject = url.parse(req.url, true);
    const fileName = "." + urlObject.pathname;
    
    if(fileName != "./favicon.ico") {
        console.log(fileName);
        const queryData = urlObject.query;
        console.log(queryData.word);

        const file = readline.createInterface({
            input: fs.createReadStream(fileName),
            output: process.stdout,
            terminal: false
        });
    
        file.on('line', (line) => {
            if(line[0] == queryData.letter) {
                newLines[numOfLines] = line;
                occorrenze++;
                numOfLines++;
            }
            
        });

        file.on('close', () => {
            console.log("Numero di righe attuali: " + numOfLines);
            
            res.write(`<p>Documento scelto: ${fileName}</p>`);
            res.write(`Lettera scelta: ${queryData.letter}<br/>`);

            if(newLines.length == 0) {
                res.write("Nessuna nuova notizia");
            } else {
                if(newLines.length > oldNumOfLines) {
                    for(let i = 0; i < newLines.length; i++) {
                        if(newLines[i] != oldLines[i]) {
                            res.write(`<p>${newLines[i]}</p>`);
                        }
                    }
                } else
                    res.write("Nessuna nuova notizia");
            }

            console.log("Numero di righe vecchie: " + oldNumOfLines);
            console.log("Occorrenze: " + occorrenze);
            if(oldNumOfLines < numOfLines) {
                oldNumOfLines = numOfLines;
                for(let i = 0; i < newLines.length; i++) {
                    oldLines[i] = newLines[i];
                }
            }
            
            console.log("Old lines " + oldLines);
            res.end();
        });
    }
});

server.listen(port, hostname, () => {
    console.log(`Server running at http://${hostname}:${port}/`);
});