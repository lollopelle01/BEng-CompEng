const Utente = require('../../entities/utente');

class IScegliNickname {

    /**
     * This method sets the {@linkcode nickname} of the given {@linkcode utente}.
     * 
     * @param {!Utente} utente - The {@linkcode Utente} to whom set the {@linkcode nickname}.
     * @param {!string} nickname - The {@linkcode string} nickname to give to the given {@linkcode utente}.
     * 
     * @returns {void} 
     * @abstract
     * */
    impostaNickName(utente, nickname) {
        throw new Error('Must be implemented by subclass!');
    }
}

module.exports = IScegliNickname
