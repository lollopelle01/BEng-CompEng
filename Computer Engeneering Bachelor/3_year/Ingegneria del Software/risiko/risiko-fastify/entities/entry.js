class Entry {

    dataOra;
    operazione;

    /**
     * @param {string} operazione 
     */
    constructor(operazione) {
        this.dataOra = new Date();
        this.operazione = operazione;
    }
}

module.exports = Entry;
