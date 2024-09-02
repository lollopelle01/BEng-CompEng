'use strict'

const gestioneLobbyController = require('../../../../controllers/accesso-gioco/gestioneLobbyController');

module.exports = async function (fastify, opts) {
  fastify.post('/:code', async function (request, reply) {
		const code = request.params.code;
    const playerName = request.body.playerName;
    console.log("Espulso: ", playerName);
    return gestioneLobbyController.rimuoviGiocatore(code, {}, { nickname: playerName });
  })
}
