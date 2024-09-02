const Territorio = require("./territorio");

class TuplaTerritori {

    /**
     * @param {Territorio} origine 
     * @param {Territorio} destinazione 
     */
    constructor(origine, destinazione) {
        this._origine = origine;
        this._destinazione = destinazione;
    }

    get origine() {
        return this._origine;
    }

    set origine(origine) {
        this._origine = origine;
    }

    get destinazione() {
        return this._destinazione;
    }

    set destinazione(destinazione) {
        this._destinazione = destinazione;
    }
}

module.exports = TuplaTerritori;
