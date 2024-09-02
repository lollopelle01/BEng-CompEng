'use strict'

const partiteManager = require('../../../controllers/accesso-gioco/accessoGiocoController').partiteManager

module.exports = async function (fastify, opts) {
  fastify.get('/:code', async function (request, reply) {
		const code = request.params.code;
    
    const result = partiteManager.getLobbyById(code);

    return result == null
      ? (partiteManager.getPartitaById(code) != null
          ? { isCominciata: true }
          : null)
      : result;
  })
}
