const Giocatore = require('../../entities/giocatore');

/**
 * @interface
 */
class IChat {

    /**
     * This method writes the given {@linkcode message} of the given {@linkcode giocatore} to the chat.
     * 
     * @param {!Giocatore} giocatore - The {@linkcode Giocatore} who has written the {@linkcode message}.
     * @param {!string} message - The {@linkcode String} containing the message of the {@linkcode giocatore}
     * 
     * @returns {void}
     * @abstract
     * */
    scriviMessaggio(giocatore, message) {
        throw new Error('Must be implemented by subclass!');
    }

}

module.exports = IChat
