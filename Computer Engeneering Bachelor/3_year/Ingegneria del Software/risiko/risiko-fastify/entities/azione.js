/**
 * @abstract
 */
class Azione {

    descrizione;

    /**
     * @param {string} descrizione 
     */
    constructor(descrizione) {
        this.descrizione = descrizione;
    }
}

module.exports = Azione;
