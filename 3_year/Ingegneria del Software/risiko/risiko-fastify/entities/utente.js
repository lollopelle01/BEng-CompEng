class Utente {

    id;
    nickname;

    /**
     * @param {number} id 
     * @param {string} nickname 
     */
    constructor(id, nickname) {
        this.id = id
        this.nickname = nickname
    }
}

module.exports = Utente