import React, { useState } from 'react';
import Lobby from './Lobby'; // Import the Lobby component
import Modal from '../shared/Modal';

const RicercaModal = ({ nickname, onClose, isOpen }) => {
  const [numPlayers, setNumPlayers] = useState('');
  const [maxTurns, setMaxTurns] = useState('');
  const [map, setMap] = useState('');
  const [language, setLanguage] = useState('');

	const [lobby, setLobby] = useState(undefined);
	const [isLobbyOpen, setIsLobbyOpen] = useState(false);

  const handleConferma = () => {
    console.log('Input 1:', numPlayers);
    console.log('Input 2:', maxTurns);
    console.log('Input 3:', map);
    console.log('Input 4:', language);

    const data = {
      playerName: nickname,
      numPlayers,
      maxTurns, 
      map,
      language,
    }

    fetch('http://127.0.0.1:3000/accesso-partita/ricerca', {
      method: 'POST', // Use the HTTP method you need (POST in this case)
      headers: {
        'Content-Type': 'application/json', // Set the content type to JSON
      },
      body: JSON.stringify(data), // Convert the JSON object to a string
    })
      .then(response => {
        if (!response.ok) {
          console.log("ok")
        }
        return response.json(); // You can change this based on the response format (JSON, text, etc.)
      })
      .then(data => {
        // Handle the response data here
        console.log(data);
        setLobby(data)
        setIsLobbyOpen(true);
      })
      .catch(error => {
        // Handle any errors here
        console.error('Error:', error);
      });

  };

  return (
    <div className="bg-white w-64 p-4 rounded-lg">
      <h2 className="text-2xl font-bold mb-4">Ricerca</h2>
      <input
        type="text"
        placeholder="Numero giocatori"
        value={numPlayers}
        onChange={(e) => setNumPlayers(e.target.value)}
        className="p-2 m-2 border border-gray-300 rounded"
      />
      <input
        type="text"
        placeholder="Turni massimi"
        value={maxTurns}
        onChange={(e) => setMaxTurns(e.target.value)}
        className="p-2 m-2 border border-gray-300 rounded"
      />
      <input
        type="text"
        placeholder="Mappa"
        value={map}
        onChange={(e) => setMap(e.target.value)}
        className="p-2 m-2 border border-gray-300 rounded"
      />
      <input
        type="text"
        placeholder="Lingua chat"
        value={language}
        onChange={(e) => setLanguage(e.target.value)}
        className="p-2 m-2 border border-gray-300 rounded"
      />
      <button
        onClick={handleConferma}
        className="p-2 bg-blue-500 text-white rounded-full hover:bg-blue-600">
        Conferma
      </button>
      <button
        onClick={onClose}
        className="p-2 bg-red-500 text-white rounded-full hover:bg-red-600">
        Close
      </button>

			<Modal isOpen={isLobbyOpen && isOpen}>
        {/* Content inside the full-screen "Lobby" modal */}
        <Lobby lobby={lobby} setLobby={setLobby} nickname={nickname} isAdmin={false} onClose={() => setIsLobbyOpen(false)}/>
      </Modal>
    </div>
  );
};

export default RicercaModal;
