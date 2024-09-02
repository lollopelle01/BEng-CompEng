const Fase = require("./fase");
const Disposizione = require("./disposizione");

class FaseDiDisposizione extends Fase {
    
    disposizioni;

    /**
     * 
     * @param {number} durataInMinuti 
     * @param {Disposizione[]} disposizioni 
     */
    constructor(durataInMinuti, disposizioni = null) {
        super(durataInMinuti);
        this.disposizioni = disposizioni;
        this.nome = "disposizione";
    }
}

module.exports = FaseDiDisposizione;
