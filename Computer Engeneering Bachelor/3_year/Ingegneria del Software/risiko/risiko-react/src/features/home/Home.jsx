import React, { useState, createContext } from 'react';
import Modal from '../shared/Modal';
import Accesso from '../accesso-gioco/Accesso';
import Gioco from '../gestione-partita/Gioco';

export const MyContext = createContext();

const Home = () => {
  const [isInGame, setIsInGame] = useState(false);
  
	const [isModalOpen, setIsModalOpen] = useState(false);
  const [isAccessoOpen, setIsAccessoOpen] = useState(false);

	const [nickname, setNickname] = useState('');

  const toggleGameStatus = () => {
    setIsInGame(!isInGame);
  };

	const handleNicknameChange = (event) => {
    setNickname(event.target.value);
  };

  return (
    <MyContext.Provider value={[ isInGame, setIsInGame ]}>
    <div className="h-screen flex flex-col items-center justify-center relative">
      {isInGame ? (
        <Gioco codice={isInGame.codice} nickname={nickname} />
      ) : (
        <div className="bg-gray-100 p-4 rounded-lg flex flex-col items-center justify-center">
          {/* Content to display when isInGame is false */}
          <h2 className='text-4xl font-bold mb-4'>RISIKO</h2>
					<button
            onClick={() => setIsModalOpen(true)}
            className="p-2 m-2 bg-blue-500 text-white rounded-full hover:bg-blue-600">
            Scegli nickname
          </button>
          <button
            onClick={() => setIsAccessoOpen(true)}
            className="p-2 m-2 bg-blue-500 text-white rounded-full hover:bg-blue-600">
            Accesso gioco
          </button>
        </div>
      )}

			<Modal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)}>
        {/* Content inside the modal */}
        <div className="flex flex-col items-center justify-center">
          <h2>Scgli un nickname</h2>
          <input
            type="text"
            value={nickname}
            onChange={handleNicknameChange}
            className="p-2 m-2 border border-gray-300 rounded"
          />
          <button
            onClick={() => setIsModalOpen(false)}
            className="p-2 px-8 bg-blue-500 text-white rounded-full hover:bg-blue-600">
            Save
          </button>
        </div>
      </Modal>

			<Modal isOpen={isAccessoOpen && isInGame == false} onClose={() => setIsAccessoOpen(false)}>
        {/* Content inside the "Accesso gioco" modal */}
        <Accesso isAccessoOpen={isAccessoOpen && isInGame == false} nickname={nickname} onClose={() => setIsAccessoOpen(false)} />
      </Modal>

      <button
        onClick={toggleGameStatus}
        className="absolute bottom-4 right-4 p-2 bg-blue-500 text-white rounded-full hover:bg-blue-600">
        Manual
      </button>
    </div>
    </MyContext.Provider>
  );
};

export default Home;
