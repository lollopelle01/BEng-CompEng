const Fase = require("./fase");
const Spostamento = require("./spostamento");

class FaseDiSpostamento extends Fase{
    
    spostamento;

    /**
     * @param {number} durataInMinuti 
     * @param {Spostamento} spostamento 
     */
    constructor(durataInMinuti, spostamento=null) {
        super(durataInMinuti);
        this.spostamento = spostamento;
        this.nome = "spostamento";
    }
}

module.exports = FaseDiSpostamento