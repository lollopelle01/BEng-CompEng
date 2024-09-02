const Azione = require('./azione');
const Territorio = require('./territorio');

class Attacco extends Azione {

    armateAttacco;
    armateDifesa;
    territorioAttacco;
    territorioDifesa;

    /**
     * @param {number} armateAttacco 
     * @param {number} armateDifesa 
     * @param {Territorio} territorioAttacco 
     * @param {Territorio} territorioDifesa 
     * @param {string} descrizione 
     */
    constructor(armateAttacco, armateDifesa, territorioAttacco, territorioDifesa, descrizione) {
        super(descrizione);
        this.armateAttacco = armateAttacco;
        this.armateDifesa = armateDifesa;
        this.territorioAttacco = territorioAttacco;
        this.territorioDifesa = territorioDifesa;
    }
}

module.exports = Attacco;
