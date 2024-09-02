const CartaObiettivo = require("./cartaObiettivo");
const Giocatore = require("./giocatore");
const PartitaCominciata = require("./partitaCominciata");
const Colore = require("./colore");

class CartaObiettivoUccisione extends CartaObiettivo {
    colore;

    /**
     * @param {!number} id 
     * @param {!string} testo 
     * @param {!Colore} colore 
     */
    constructor(id, testo, colore){
        super(id, testo);
        this.colore = colore;
    }

    /**
     * @param {!Giocatore} giocatore 
     * @param {!PartitaCominciata} partitaCominciata
     * @returns {boolean}
     */
    checkConseguimento(giocatore, partitaCominciata) {
        // TODO: implement method when entities are set to go --> arge controlla
        // Se colore è vivo
        if(partitaCominciata.isColoreVivo(this.colore)){
            return false
        } else {
            // Se colore è ucciso da un altro
            if(partitaCominciata.getUccisore(this.colore)!==giocatore.colore){
                // Se ha conquistato 24 territori
                if(partitaCominciata.mappa.getTerritoriDiGiocatore(giocatore).length>=24) {
                    return true
                } else {
                    return false
                }
            } else{   // Se colore è ucciso da lui
                return true
            }
        }
    }

    /**
     * This method returns the targeted {@linkcode Giocatore}'s {@linkcode Colore}.
     * 
     * @returns {string}
     */
    getColoreDaUccidere() {
        return this.colore;
    }
}

module.exports = CartaObiettivoUccisione