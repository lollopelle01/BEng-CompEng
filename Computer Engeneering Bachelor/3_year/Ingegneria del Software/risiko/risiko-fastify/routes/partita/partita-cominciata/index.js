'use strict'

const partiteManager = require('../../../controllers/accesso-gioco/accessoGiocoController').partiteManager

module.exports = async function (fastify, opts) {
  fastify.get('/wss/:id', async function (request, reply) {
    const id = request.params.id;
    const url = {
      url: partiteManager.getUrlWebSocketServerDiPartita(id)
    };
    return url;
  })
}
