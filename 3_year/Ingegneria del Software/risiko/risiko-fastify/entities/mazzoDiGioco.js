const CartaCombinazione = require("./cartaCombinazione");

class MazzoDiGioco {
    id;
    carteCombinazione;

    /**
     * @param {number} id 
     * @param {CartaCombinazione[]} carteCombinazione 
     */
    constructor(id, carteCombinazione) {
        this.id = id;
        this.carteCombinazione = carteCombinazione;
        this.mescola();
    }

    /**
     * This methods shuffles the {@linkcode MazzoDiGioco}.
     * 
     * @returns {void}
     */
    mescola() {
        let i, j;

        for (i = this.carteCombinazione.length - 1; i > 0; i--) { 
            j = Math.floor(Math.random() * (i + 1)); 
            [this.carteCombinazione[i], this.carteCombinazione[j]] = [this.carteCombinazione[j], this.carteCombinazione[i]]; 
        }

        return;
    }

    /**
     * This methods draws a single card from the {@linkcode MazzoDiGioco}.
     * 
     * @returns {?CartaCombinazione}
     */
    pesca() {
        return this.carteCombinazione.pop();
    }

    /**
     * This methods puts the given {@linkcode carteCombinazione} into the {@linkcode MazzoDiGioco}
     * 
     * @param {!CartaCombinazione[]} carteCombinazione 
     * @returns {void}
     */
    reinserisci(carteCombinazione) {
        if (carteCombinazione.length != 0) {
            this.carteCombinazione.push(...carteCombinazione);
            this.mescola();
        }

        return;
    }
}

module.exports = MazzoDiGioco