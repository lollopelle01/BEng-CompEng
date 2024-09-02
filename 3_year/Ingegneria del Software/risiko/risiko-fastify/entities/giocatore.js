const Utente = require("./utente");
const Colore = require("./colore");
const CartaCombinazione = require("./cartaCombinazione");
const CartaObiettivo = require("./cartaObiettivo");

class Giocatore extends Utente {

  colore;
  armateDaDisporre;
  /**
   * @type {CartaCombinazione[]}
   */
  carteCombinazione;
  /**
   * @type {CartaObiettivo}
   */
  obiettivo;

  /**
   * @param {number} id 
   * @param {string} nickname 
   * @param {Colore} colore 
   */
  constructor(id, nickname, colore) {
    super(id, nickname);
    this.colore = colore;
    this.armateDaDisporre = 0;
    this.carteCombinazione = [];
    this.obiettivo = null;
  }
}

module.exports = Giocatore;
