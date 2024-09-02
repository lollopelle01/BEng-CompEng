const Carta = require("./carta");
const CartaObiettivo = require("./cartaObiettivo");
const CartaTerritorio = require("./cartaTerritorio");
const CartaCombinazione = require("./cartaCombinazione");

class Mazzo {
    id;
    carte;

    /**
     * @type {CartaObiettivo[]}
     */
    carteObiettivo;
    /**
     * @type {CartaTerritorio[]}
     */
    carteTerritorio;
    /**
     * @type {CartaCombinazione[]}
     */
    carteCombinazione;


    /**
     * @param {number} id 
     * @param {Carta[]} carte 
     */
    constructor(id, carte) {
        this.id = id;
        this.carte = carte;

        this.carteObiettivo = [];
        this.carteTerritorio = [];
        this.carteCombinazione = carte;

        //this.carte.forEach(this._assign);
    }

    /**
     * Adds the {@linkcode carta} to the right array.
     * 
     * @param {Carta} carta
     * @returns {void}
     */
    _assign(carta) {
        if (carta instanceof CartaObiettivo) {
            this.carteObiettivo.push(carta);
        } else if (carta instanceof CartaCombinazione) {
            this.carteCombinazione.push(carta);
            if (carta instanceof CartaTerritorio) {
                this.carteTerritorio.push(carta);
            }
        } else {
            throw new Error(`Unidentified carta instance: ${carta.id}`);
        }

        return;
    }
}

module.exports = Mazzo;
