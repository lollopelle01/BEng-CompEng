const Lingua = require('./lingua');
const Mappa = require('./mappa');

class OpzioniPartita {

    numGiocatoriMassimi;
    turniMassimi;
    lingua;
    mappa;

    /** 
     * @param {number} numGiocatoriMassimi 
     * @param {number} turniMassimi 
     * @param {Lingua} lingua 
     * @param {Mappa} mappa 
     */
    constructor(numGiocatoriMassimi, turniMassimi, lingua, mappa) {
        this.numGiocatoriMassimi = numGiocatoriMassimi;
        this.turniMassimi = turniMassimi;
        this.lingua = lingua;
        this.mappa = mappa;
    }
}

module.exports = OpzioniPartita;
