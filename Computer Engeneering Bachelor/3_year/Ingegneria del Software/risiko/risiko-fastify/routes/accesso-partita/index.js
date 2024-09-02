'use strict'

const OpzioniPartita = require('../../entities/opzioniPartita')

const accessoGiocoController = require('../../controllers/accesso-gioco/accessoGiocoController')

module.exports = async function (fastify, opts) {
  fastify.post('/:mode', async function (request, reply) {
    const mode = request.params.mode;

    switch (mode) {
      case 'ricerca': {
        const nomeGiocatore = request.body.playerName;

        const opzioni = new OpzioniPartita(
          request.body.numPlayers,
          request.body.maxTurns,
          request.body.language,
          request.body.map,
        );

        return accessoGiocoController.ricercaPartita(opzioni, {
          nickname: nomeGiocatore
        });
      }

      case 'crea': {
        const nomeGiocatore = request.body.playerName;

        const opzioni = new OpzioniPartita(
          request.body.numPlayers,
          request.body.maxTurns,
          'en',
          request.body.map,
        );

        return accessoGiocoController.creaPartita(opzioni, {
          nickname: nomeGiocatore
        });
      }

      case 'unione': {
        const nomeGiocatore = request.body.playerName;
        const codice = request.body.code;

        return accessoGiocoController.unionePartita(codice, {
          nickname: nomeGiocatore
        });
      }
    
      default:
        break;
    }

  })
}
