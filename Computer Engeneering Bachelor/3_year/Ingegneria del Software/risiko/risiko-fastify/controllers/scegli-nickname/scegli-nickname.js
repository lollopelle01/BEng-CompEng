const IScegliNickname = require("../../interfaces/scegli-nickname/i-scegli-nickname");
const Utente = require('../../entities/utente');

class ScegliNicknameController extends IScegliNickname{

    constructor() {}

    /**
     * @param {!Utente} utente - The {@linkcode Utente} to whom set the {@linkcode nickname}.
     * @param {!string} nickname - The {@linkcode string} nickname to give to the given {@linkcode utente}.
     * 
     * @returns {void} 
     * @override
     */
    impostaNickName(utente, nickname) {
        //TODO
    }
}

module.exports = ScegliNicknameController