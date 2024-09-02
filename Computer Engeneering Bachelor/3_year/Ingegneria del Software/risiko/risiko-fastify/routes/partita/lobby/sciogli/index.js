'use strict'

const gestioneLobbyController = require('../../../../controllers/accesso-gioco/gestioneLobbyController');

module.exports = async function (fastify, opts) {
  fastify.post('/:code', async function (request, reply) {
		const code = request.params.code;
    console.log("request sciogli");
    return gestioneLobbyController.sciogliLobby(code, {});
  })
}
