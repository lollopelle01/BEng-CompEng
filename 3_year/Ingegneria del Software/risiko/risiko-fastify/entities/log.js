const Entry = require('./entry');

class Log {

    /**
     * @type {Entry[]}
     */
    entries;

    constructor(){
        this.entries = []
    }

    /**
     * This method adds an {@linkcode Entry} for the given {@linkcode operazione}.
     * 
     * @param {string} operazione 
     */
    addEntry(operazione) {
        this.entries.push(new Entry(operazione));
    }
}

module.exports = Log