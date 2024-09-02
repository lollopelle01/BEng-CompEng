const Giocatore = require("../../entities/giocatore");
const PartitaCominciata = require("../../entities/partitaCominciata");

class ChatController {

    constructor() {}

     /**
     * This method writes the given {@linkcode message} of the given {@linkcode giocatore} to the chat in the given {@linkcode partitaCominciata}.
     * 
     * @param {PartitaCominciata} partitaCominciata - The {@linkcode PartitaCominciata} where the given {@linkcode giocatore} has requested to write the given {@linkcode message}.
     * @param {Giocatore} giocatore - The {@linkcode Giocatore} who has written the {@linkcode message} in the given {@linkcode partitaCominciata}.
     * @param {string} message - The {@linkcode String} containing the message of the {@linkcode giocatore} in the given {@linkcode partitaCominciata}.
     * 
     * @returns {void}
     * @abstract
     * */
    scriviMessaggio(partitaCominciata, giocatore, message) {
        partitaCominciata.chat.scriviMessaggio(giocatore, message);
    }
}

module.exports = ChatController
