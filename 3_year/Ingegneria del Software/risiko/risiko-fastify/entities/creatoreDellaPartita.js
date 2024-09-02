const Giocatore = require("./giocatore");
const Colore = require("./colore");

class CreatoreDellaPartita extends Giocatore{

    /**
     * Per utility
     */
    isCreatore;

    /**
     * @param {number} id 
     * @param {string} nickname 
     * @param {Colore} colore 
     * @param {number} armateDaDisporre 
     */
    constructor(id, nickname, colore, armateDaDisporre) {
        super(id, nickname, colore, armateDaDisporre);
        this.isCreatore = true;
    }

}

module.exports = CreatoreDellaPartita