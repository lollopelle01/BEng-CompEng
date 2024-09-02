const Giocatore = require("./giocatore");
const CartaTerritorio = require("./cartaTerritorio");

class Territorio {

    id;
    nome;
    numArmate;
    possessore;
    cartaTerritorio;

    /**
     * @param {number} id 
     * @param {string} nome 
     * @param {int} numArmate 
     * @param {Giocatore} possessore 
     * @param {CartaTerritorio} cartaTerritorio 
     */
    constructor(id, nome, numArmate, possessore, cartaTerritorio) {
        this.id = id;
        this.nome = nome;
        this.numArmate = numArmate;
        this.possessore = possessore;
        this.cartaTerritorio = cartaTerritorio;
    }
}

module.exports = Territorio;
