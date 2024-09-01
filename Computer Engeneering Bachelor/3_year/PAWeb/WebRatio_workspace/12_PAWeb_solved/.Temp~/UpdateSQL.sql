-- LineaOrdine.prezzo_parziale [ent8#att29]
drop view "00858818"."LINEAORDINE_PREZZO_PARZIALE_VI";
create view "00858818"."LINEAORDINE_PREZZO_PARZIALE_VI" as
select AL1."ID" as "ID", AL1."QUANTITA" * AL2."PREZZO" as "DER_ATTR"
from  "00858818"."LINEA_ORDINE" AL1 
               left outer join "00858818"."SUPPORTO" AL2 on AL1."SUPPORTO_ID"=AL2."ID";


-- Ordine.totale [ent9#att30]
drop view "00858818"."ORDINE_TOTALE_VIEW";
create view "00858818"."ORDINE_TOTALE_VIEW" as
select AL1."ID" as "ID", sum(AL3."DER_ATTR") as "DER_ATTR"
from  "00858818"."ORDINE" AL1 
               left outer join "00858818"."LINEA_ORDINE" AL2 on AL1."ID"=AL2."ID_ORDINE"
               left outer join "00858818"."LINEAORDINE_PREZZO_PARZIALE_VI" AL3 on AL2."ID"=AL3."ID"
group by AL1."ID";


