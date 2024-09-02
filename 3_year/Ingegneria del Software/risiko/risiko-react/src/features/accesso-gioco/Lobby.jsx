import React, { useEffect, useState, useContext } from 'react';
import { MyContext } from '../home/Home';

const Lobby = ({ isAdmin, onClose, lobby, nickname }) => {
  const [actualLobby, setActualLobby] = useState(lobby);
  const [players, setplayers] = useState(actualLobby ? actualLobby.giocatori : []);

  const [isInGame, setIsInGame] = useContext(MyContext);

  useEffect(() => {
    const callback = () => {

      fetch('http://127.0.0.1:3000/partita/lobby/' + actualLobby.codice, {
        method: 'GET',
      })
        .then(response => {
          if (!response.ok) {
            console.log("ok");
          }
          return response.json(); // You can change this based on the response format (JSON, text, etc.)
        })
        .then(data => {
          // Handle the response data here
          console.log(data);

          if (data && data.isCominciata) {
            onClose();
            
            setTimeout(() => {
              // passa alla partita
              setIsInGame({
                codice: actualLobby.codice
              });
            }, 1);
          } else if (data == null) {
            onClose();
          } else {
            console.log(
              data.giocatori.map(p => p.nickname),
              nickname,
              data.giocatori.map(p => p.nickname).includes(nickname)
            );
            if (!data.giocatori.map(p => p.nickname).includes(nickname)) {
              onClose();
              return;
            } else {
              setActualLobby(data);
              setplayers(data.giocatori);
              setTimeout(callback, 3000);
            }
            
          }

        })
        .catch(error => {
          // Handle any errors here
          console.error('Error:', error);
        });
    };

    setTimeout(callback, 3000);
  }, []);

  const handleRemovePlayer = (playerName) => {
    // Implement the logic to remove the player, if isAdmin is true.
    console.log(`Removed player: ${playerName}`);

    fetch('http://127.0.0.1:3000/partita/lobby/espulsione/' + actualLobby.codice, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json', // Set the content type to JSON
      },
      body: JSON.stringify({
        playerName
      }),
    })
      .then(response => {
      })
      .catch(error => {
        // Handle any errors here
        console.error('Error:', error);
      });
  };

  const handleStartGame = () => {
    // Implement the logic to start the game, if isAdmin is true.
    console.log('Avvia');
    fetch('http://127.0.0.1:3000/partita/lobby/avvia/' + actualLobby.codice, {
        method: 'POST',
      })
        .then(response => {
        })
        .catch(error => {
          // Handle any errors here
          console.error('Error:', error);
        });
  };

  const handleLeaveLobby = () => {
    // Implement the logic to leave the lobby or close the modal, based on isAdmin.
    
    console.log('nickname', nickname);
    
    if (isAdmin) {
      
      fetch('http://127.0.0.1:3000/partita/lobby/sciogli/' + actualLobby.codice, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json', // Set the content type to JSON
        },
        body: JSON.stringify({
          playerName: nickname
        }),
      })
        .then(response => {
        })
        .catch(error => {
          // Handle any errors here
          console.error('Error:', error);
        });

      onClose(); // Close the modal if isAdmin is true.
    } else {
      console.log('Abbandona');

      fetch('http://127.0.0.1:3000/partita/lobby/abbandona/' + actualLobby.codice, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json', // Set the content type to JSON
        },
        body: JSON.stringify({
          playerName: nickname
        }),
      })
        .then(response => {
        })
        .catch(error => {
          // Handle any errors here
          console.error('Error:', error);
        });

      onClose();
    }
  };

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-gray-900 bg-opacity-50">
      <div className="bg-white w-96 p-4 rounded-lg">
        <h2 className="text-2xl font-bold mb-4">
          {isAdmin ? 'Gestione lobby' : 'Lobby'}
        </h2>

        {actualLobby.codice}
        <hr />

        <ul>
          {players.map((player, i) => (
            <li key={player.nickname + i} className="flex justify-between items-center">
              <span>{player.nickname}</span>
              {isAdmin && player.nickname != nickname && (
                <button
                  onClick={() => handleRemovePlayer(player.nickname)}
                  className="p-2 bg-red-500 text-white rounded-full hover:bg-red-600"
                >
                  Rimuovi
                </button>
              )}
            </li>
          ))}
        </ul>

        {isAdmin ? (
          <div className="mt-4">
            <button
              onClick={handleStartGame}
              className="p-2 bg-green-500 text-white rounded-full hover:bg-green-600"
            >
              Avvia
            </button>
            <button
              onClick={handleLeaveLobby}
              className="p-2 bg-blue-500 text-white rounded-full hover:bg-blue-600 ml-2"
            >
              Sciogli lobby
            </button>
          </div>
        ) : (
          <button
            onClick={handleLeaveLobby}
            className="p-2 bg-red-500 text-white rounded-full hover:bg-red-600 mt-4"
          >
            Abbandona
          </button>
        )}
      </div>
    </div>
  );
};

export default Lobby;
