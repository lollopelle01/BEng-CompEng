GENERATORE DAO

-Copiare i file nella cartella del progetto
-Modificare DAOgenerator sulla base dei commenti presenti al suo interno
-Eseguire

Cosa fa:
-generazione DTO
-generazione DAO
-generazione implementazione DB2 dell'oggetto

TODO:
0) usare generator mettendo anche i campi di foreign key
1) DTO eventuali campi privati di riferimenti/set
2) DTO import automatici (ctrl+shift+o) 
3) DTO toString usando field e non getter and setter (alt+shift+s,s)
4) DTO generazione dei metodi setter/getter per tutti (alt+shift+s,r,alt-a)
5) DTO costruttori con field, solo quelli semplici, si FK, 
       no riferimenti java, inizializzare set vuoti (alt+shift+o)
6) DTO costruttore vuoto che inizializza i set (alt+shift+s,c)
7) DAO mettere vincoli sulle tabelle
   - FK modificare create table, aggiungere metodo readByFk (con @Override così lo aggiunge da solo all'interfaccia)
   - UNIQUE modificare create table, aggiungere metodo readByUnique (con @Override così lo aggiunge da solo all'interfaccia)
   - CHECK modificare create table
8) modifica del file Db2DAOFactory 
   - scrivere i getter per dei DAO per DB2 (con @Override così lo aggiunge da solo alla classe astratta)
9) DAOTest

10) Se serve un proxy creare una classe vuota, farle estendere il DTO, generare i costruttori per 
    delega (al-shift-s,c), toString non serve perchè quello della superclasse usa i getter, implementare
    solo il getter del campo da caricare lazy (basta iniziare a scriverne il nome e ctrl-spazio lo crea 
    con @Override). 
    
    
    In genere basta questo (citta-maratone)
    
    Db2MaratonaProxy (dalla parte many 2 one)
    @Override
	public CittaDTO getCitta() {
		if (!caricato) {
			this.setCitta(new Db2CittaDAO().read(this.getId_citta()));
		}
		caricato = true;
		return super.getCitta();
	}
	@Override
	public void setCitta(CittaDTO citta) {
		caricato = true;
		super.setCitta(citta);
	}
	
	Db2CittaProxy (dalla parte one 2 many)
	@Override
	public Set<MaratonaDTO> getMaratone() {
		if (!caricato) {
			this.setMaratone(new Db2MaratonaDAO().readByIdCitta(this.getId()));
		}
		caricato = true;
		return super.getMaratone();
	}	
	@Override
	public void setMaratone(Set<MaratonaDTO> maratone) {
		caricato = true;
		super.setMaratone(maratone);
	}