const Fase = require("./fase");
const Attacco = require("./attacco");

class FaseDiAttacco extends Fase {
    
    attacchi;

    /**
     * 
     * @param {number} durataInMinuti 
     * @param {Attacco[]} attacchi 
     */
    constructor(durataInMinuti, attacchi = null) {
        super(durataInMinuti);
        this.attacchi = attacchi;
        this.nome = "attacco";
    }
}

module.exports = FaseDiAttacco