const Giocatore = require('./giocatore');
const Messaggio = require('./messaggio');

class Chat {

    messaggi;

    /**
     * @param {Messaggio[]} messaggi 
     */
    constructor(messaggi){
        this.messaggi = messaggi;
    }

    /**
     * This method adds the given {@linkcode messaggio} to the {@linkcode Chat}.
     * 
     * @param {Giocatore} giocatore 
     * @param {string} messaggio 
     * 
     * @returns {void}
     */
    scriviMessaggio(giocatore, messaggio){
        this.messaggi.push(new Messaggio(giocatore, messaggio, new Date()));
    }
}

module.exports = Chat