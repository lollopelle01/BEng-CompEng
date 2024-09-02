const CartaCombinazione = require("../../entities/cartaCombinazione");
const Combinazione = require("../../entities/combinazione");
const Figura = require("../../entities/figura");
const Giocatore = require("../../entities/giocatore");
const PartitaCominciata = require("../../entities/partitaCominciata");

class CombinazioneController {

    _isCombinazioneValida(carte){ // sono 3 carte !!!
        let gruppi = new Map();
        gruppi[Figura.Jolly]=[];
        gruppi[Figura.Cannone]=[];
        gruppi[Figura.Cavallo]=[];
        gruppi[Figura.Fante]=[];

        for(let i=0; i<carte.length; i++){
            gruppi[carte[i].figura].push(carte[i]);
        }

        // Se ho un jolly
        if(gruppi[Figura.Jolly].length==1){
            // Se ho almeno due carte uguali
            if(gruppi[Figura.Cannone].length==2 || gruppi[Figura.Cavallo].length==2 || gruppi[Figura.Fante].length==2){
                if(gruppi[Figura.Cannone].length==2){ // Se gli altri 2 sono Cannoni
                    // console.log("Ho promosso:")
                    // console.log(gruppi);
                    // console.log("-----------------------------------\n\n");
                    return true;
                }
                else if(gruppi[Figura.Cavallo].length==2){ // Se gli altri 2 sono Cavalli
                    // console.log("Ho promosso:")
                    // console.log(gruppi);
                    // console.log("-----------------------------------\n\n");
                    return true;
                }
                else if(gruppi[Figura.Fante].length==2){ // Se gli altri 2 sono Fanti
                    // console.log("Ho promosso:")
                    // console.log(gruppi);
                    // console.log("-----------------------------------\n\n");
                    return true
                }
            }
        }
        else{ // Non ho jolly
            if(gruppi[Figura.Cannone].length==3){ // Se 3 Cannoni
                // console.log("Ho promosso:")
                // console.log(gruppi);
                // console.log("-----------------------------------\n\n");
                return true;
            }
            else if(gruppi[Figura.Cavallo].length==3){ // Se 3 Cavalli
                // console.log("Ho promosso:")
                // console.log(gruppi);
                // console.log("-----------------------------------\n\n");
                return true;
            }
            else if(gruppi[Figura.Fante].length==3){ // Se 3 Fanti
                // console.log("Ho promosso:")
                // console.log(gruppi);
                // console.log("-----------------------------------\n\n");
                return true;
            }
            else if(gruppi[Figura.Cannone].length==1 && gruppi[Figura.Cavallo].length==1 && gruppi[Figura.Fante].length==1){ // Se 3 diversi
                // console.log("Ho promosso:")
                // console.log(gruppi);
                // console.log("-----------------------------------\n\n");
                return true;
            }
        }
        // console.log("NON ho promosso:")
        // console.log(gruppi);
        // console.log("-----------------------------------\n\n");
        return false;
    }

    /**
     * This method returns all the {@linkcode Combinazione} available to the given {@linkcode giocatore} in the given {@linkcode partitaCominciata}.
     * 
     * @param {PartitaCominciata} partitaCominciata - The {@linkcode PartitaCominciata} where the given {@linkcode giocatore} has requested to see all available {@linkcode Combinazione}.
     * @param {Giocatore} giocatore - The {@linkcode Giocatore} to whom check all {@linkcode Combinazione}.
     * 
     * @returns {!Combinazione[]}
     * @abstract
     * */
    elencaCombinazioniValide(partitaCominciata, giocatore) {
        /**
         * COMBIAZIONI VALIDE SOLO PER MAPPA PRINCIPALE
         */

        /*** TEORICAMENTE VALIDO PER QUALUNQUE NUMERO DI COMBINAZIONI ***/
        let combinazioni_valide = [];
        let combinazioni_possibili = [];
        let carte = giocatore.carteCombinazione.map(obj => ({ ...obj })); // copia delle carte

        // trovare tutte possibili combinazioni
        for (let i = 0; i < carte.length - 2; i++) {
            for (let j = i + 1; j < carte.length - 1; j++) {
                for (let k = j + 1; k < carte.length; k++) {
                    const combinazioneNonOrdinata = [carte[i], carte[j], carte[k]];
                    const combinazioneOrdinata = combinazioneNonOrdinata.slice().sort((a, b) => (a.id - b.id));

                    combinazioni_possibili.push(combinazioneOrdinata);
                }
            }
        }

        // console.log("--In mezzo---------------------------------------------")
        // //console.log(combinazioni_possibili);
        // let combinazione;
        // let i=0;
        // for(const c of combinazioni_possibili){
        //     console.log(i + ")\t\t" + c[0].figura + "(" + c[0].id + "), " 
        //                             + c[1].figura + "(" + c[1].id + "), " 
        //                             + c[2].figura + "(" + c[2].id + ") "); 
        //     i++;
        // }

        //per ogni combinazione, se valida, aggiungi
        for(const c of combinazioni_possibili){
            if(this._isCombinazioneValida(c)){
                combinazioni_valide.push(new Combinazione(c));
            }
        }

        return combinazioni_valide;
    }

    /**
     * This method uses the given {@linkcode combinazione} of the given {@linkcode giocatore}, returning the corresponding number of armies,  in the given {@linkcode partitaCominciata}.
     * 
     * @param {PartitaCominciata} partitaCominciata - The {@linkcode PartitaCominciata} where the given {@linkcode giocatore} has requested to play the given {@linkcode Combinazione}.
     * @param {Giocatore} giocatore - The {@linkcode Giocatore} who plays the {@linkcode combinazione}.
     * @param {Combinazione} combinazione - The {@linkcode Combinazione} played by the {@linkcode giocatore}
     * 
     * @returns {!number}
     * @abstract
     * */
    utilizzaCombinazione(partitaCominciata, giocatore, combinazione){
        partitaCominciata.utilizzaCombinazione(combinazione);
    }
}

module.exports = CombinazioneController
