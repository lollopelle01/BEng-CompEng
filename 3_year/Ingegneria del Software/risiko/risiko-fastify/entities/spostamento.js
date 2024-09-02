const Azione = require('./azione');
const Territorio = require('./territorio');

class Spostamento extends Azione {

    numeroArmate;
    territorioDa;
    territorioA;


    /**
     * @param {number} numeroArmate 
     * @param {Territorio} territorioDa 
     * @param {Territorio} territorioA 
     * @param {string} descrizione 
     */
    constructor(numeroArmate, territorioDa, territorioA, descrizione) {
        this.numeroArmate = numeroArmate;
        this.territorioDa = territorioDa;
        this.territorioA = territorioA;
    }
}

module.exports = Spostamento;
