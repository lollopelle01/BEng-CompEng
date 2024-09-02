const Giocatore = require("./giocatore");
const Territorio = require("./territorio");

class Regione {

    id;
    nome;
    armateBonus;
    possessore;
    territori;

    /**
     * @param {number} id 
     * @param {string} nome 
     * @param {number} armateBonus 
     * @param {Territorio[]} territori 
     * @param {Giocatore} possessore 
     */
    constructor(id, nome, armateBonus, territori, possessore = null) {
        this.id = id;
        this.nome = nome;
        this.armateBonus = armateBonus;
        this.possessore = possessore; // ovviamente giocatore
        this.territori = territori;
    }
}

module.exports = Regione;
