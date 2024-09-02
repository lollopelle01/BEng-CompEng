GENERATORE HIBERNATE

-Copiare i file nella cartella del progetto
-Modificare HIBERNATEgenerator sulla base dei commenti presenti al suo interno
-Eseguire

Cosa fa:
-generazione hbm.xml (incompleto, leggere sotto)
-generazione bean relativo

TODO:
0) usare generator SENZA mettere campi di FK e set
1) BEAN eventuali campi privati di riferimenti/set
2) BEAN import automatici (ctrl+shift+o) 
3) BEAN costruttori con field, solo quelli semplici, no riferimenti, 
   no id di PK, chiamare costruttore vuoto (alt+shift+o)
4) BEAN generazione dei metodi setter/getter per tutti (alt+shift+s,r,alt-a)
5) BEAN costruttore vuoto che inizializza i set (alt+shift+s,c)
5) BEAN toString (alt+shift+s,s)
7) XML mettere vincoli sulle tabelle, aggiungendo relazioni, FK, unique e not null
9) HibernateTest
10)Togliere autogenerazione tabelle, copiare gli statement sql generati 