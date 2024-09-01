GENERATORE JDBC

-Copiare i file nella cartella del progetto
-Modificare JDBCgenerator sulla base dei commenti presenti al suo interno
-Eseguire

Cosa fa:
-generazione bean
-generazione file di gestione del bean

TODO:
0) usare generator mettendo anche i campi di foreign key
2) Bean import automatici (ctrl+shift+o) 
3) Bean toString usando field e non getter and setter (alt+shift+s,s)
4) Bean generazione dei metodi setter/getter per tutti (alt+shift+s,r,alt-a)
5) Bean costruttori con field, si FK, 
7) Repository mettere vincoli sulle tabelle
   - FK modificare create table, aggiungere metodo readByFk (con @Override così lo aggiunge da solo all'interfaccia)
   - UNIQUE modificare create table, aggiungere metodo readByUnique (con @Override così lo aggiunge da solo all'interfaccia)
   - CHECK modificare create table

Esempio:
CREATE TABLE partita (
	squadra_casa BIGINT NOT NULL,
	squadra_ospite BIGINT NOT NULL,
	stadio BIGINT NOT NULL,
	esito VARCHAR(1) NOT NULL,
	data DATE NOT NULL, 
	PRIMARY KEY (squadra_casa, squadra_ospite),
	UNIQUE (squadra_casa, squadra_ospite), 
	FOREIGN KEY (stadio) REFERENCES stadio(codice_stadio), 
	FOREIGN KEY (squadra_casa) REFERENCES Squadra(codice_squadra), 
	FOREIGN KEY (squadra_ospite) REFERENCES Squadra(codice_squadra),
	CHECK (esito in ('1','x','2')),
	CHECK (squadra_casa <> squadra_ospite)
)