const PartitaCominciata = require('../../entities/partitaCominciata');

/**
 * @interface
 */
class ICreaGioco {

    /**
     * This method creates a new WebSocketServerThread and returns the URL to where the given {@linkcode PartitaCominciata} will be hosted.
     * 
     * @param {!PartitaCominciata} partitaCominciata - The {@linkcode PartitaCominciata} needing a WebSocketServerThread to be played.
     * 
     * @returns {!Promise<string>}
     * @abstract
     * */
    creaWebSocketServer(partitaCominciata) {
        throw new Error('Must be implemented by subclass!');
    }

}

module.exports = ICreaGioco
