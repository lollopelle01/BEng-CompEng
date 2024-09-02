const Fase = require("./fase");
const Combinazione = require("./combinazione");

class FaseDiCombinazione extends Fase{
    
    combinazione;

    /**
     * @param {number} durataInMinuti 
     * @param {Combinazione} combinazione 
     */
    constructor(durataInMinuti, combinazione = null) {
        super(durataInMinuti);
        this.combinazione = combinazione;
        this.nome = "combinazione";
    }
}

module.exports = FaseDiCombinazione