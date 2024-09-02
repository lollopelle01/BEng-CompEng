const Regione = require("./regione");
const Mazzo = require("./mazzo");
const MazzoDiGioco = require("./mazzoDiGioco");
const TipoMappa = require("./tipoMappa");
const Territorio = require("./territorio");
const Giocatore = require("./giocatore");

class Mappa {

    immagine;
    territoriConfinanti;
    regioni;
    mazzo;
    mazzoDiGioco;
    tipoMappa;

    /**
     * 
     * @param {MediaImage} immagine 
     * @param {Map<Territorio, Territorio[]>} territoriConfinanti 
     * @param {Regione[]} regioni 
     * @param {Mazzo} mazzo 
     * @param {TipoMappa} tipoMappa 
     */
    constructor(immagine, territoriConfinanti, regioni, mazzo, tipoMappa) {
        this.immagine = immagine;
        this.territoriConfinanti = territoriConfinanti;
        this.regioni = regioni;
        this.mazzo = mazzo;
        this.mazzoDiGioco = new MazzoDiGioco(1, mazzo.carteCombinazione);
        this.tipoMappa = tipoMappa;
    }

    /**
     * This method returns all neighboring {@linkcode Territorio} of the given {@linkcode territorio}.
     * 
     * @param {Territorio} territorio 
     * 
     * @returns {Territorio[] | undefined}
     */
    territoriConfinanti(territorio) {
        return this.territoriConfinanti.get(territorio);
    }

    /**
     * This method return all the {@linkcode Territorio} in a given map.
     * 
     * @returns {Territorio[]}
     */
    getTuttiTerritori() {
        return Array.from(this.territoriConfinanti.keys());
    }

    /**
     * This method retursn all {@linkcode Territorio} possessed by the given {@linkcode giocatore}.
     * 
     * @param {Giocatore} giocatore 
     * @returns 
     */
    territoriDiGiocatore(giocatore) {
        return this.getTuttiTerritori().filter(territorio => territorio.possessore === giocatore);
    }
}

module.exports = Mappa;
