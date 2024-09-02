const Azione = require('./azione');
const Territorio = require('./territorio');

class Disposizione extends Azione {

    numeroArmate;
    territorio;

    /**
     * @param {number} numeroArmate 
     * @param {Territorio} territorio 
     * @param {string} descrizione 
     */
    constructor(numeroArmate, territorio, descrizione) {
        super(descrizione);
        this.numeroArmate = numeroArmate;
        this.territorio = territorio;
    }
}

module.exports = Disposizione;
