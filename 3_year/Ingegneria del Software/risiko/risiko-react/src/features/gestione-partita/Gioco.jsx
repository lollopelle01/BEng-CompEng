import React, { useState, useEffect, useCallback } from 'react';
import ListOfActions from './ListOfActions';
import Map from './Map';
import Chat from './Chat'; // Import the Chat component
import ListOfUsers from './ListOfUsers'; // Import the ListOfUsers component
import ScegliCombinazioneModal from './ScegliCombinazioneModal';
import DispositionModal from './Disposizione';
import AttackPhaseModal from './Attacco';
import MovementPhaseModal from './Spostamento';

const Gioco = ({ codice, nickname }) => {

	// State for WebSocket connection and messages
  const [socket, setSocket] = useState(window.ws);

  const [actions, setActions] = useState([]);

	const [marks, setMarks] = useState([
    // { name: 'Mark 1', value: 1, x: 100, y: 150, color: 'red' },
    // { name: 'Mark 2', value: 2, x: 200, y: 250, color: 'blue' },
    // { name: 'Mark 3', value: 3, x: 300, y: 350, color: 'green' },
  ]);

  const [users, setUsers] = useState([
    // { name: 'User 1', color: 'red', territories: 5, armies: 20 },
    // { name: 'User 2', color: 'blue', territories: 8, armies: 15 },
    // { name: 'User 3', color: 'green', territories: 6, armies: 25 },
  ]);

	const phases = ['combination phase', 'disposition phase', 'attack phase', 'movement phase'];
	const [currentPhaseIndex, setCurrentPhaseIndex] = useState(0);
  
  const [remainingArmates, setRemainingArmates] = useState(10);

	const [gameState, setGameState] = useState('combination phase'); // Initial state is 'combination phase'

  // Function to handle incoming messages
  const handleIncomingMessage = (event) => {
    const receivedMessage = JSON.parse(event.data);
    console.log('Received message:', receivedMessage);

    function flattenArray(arr) {
      return arr.reduce((acc, val) => Array.isArray(val) ? acc.concat(flattenArray(val)) : acc.concat(val), []);
    }

    if (receivedMessage.azione.giocatore) {
      console.log(actions);
      window.newActions = window.newActions == undefined ? [] : [...window.newActions];
      window.newActions.push(
        `${receivedMessage.azione.giocatore.nickname}: ${receivedMessage.azione.metodo}`,
      );
      console.log(window.newActions);
      setActions(window.newActions);
    }
    // Manage turn
    if (receivedMessage.partitaCominciata.turnoCorrente.giocatore.nickname !== nickname) {
      setGameState('not my turn');
    }
    else {
      switch (receivedMessage.partitaCominciata.turnoCorrente.faseAttuale.nome) {
        case 'combinazione':
          setGameState('combination phase');
          setCurrentPhaseIndex(0);
          break;
        case 'disposizione':
          setGameState('disposition phase');
          setCurrentPhaseIndex(1);
          break;
        case 'attacco':
          setGameState('attack phase');
          setCurrentPhaseIndex(2);
          break;
        case 'spostamento':
          setGameState('movement phase');
          setCurrentPhaseIndex(3);
          break;
      
        default:
          break;
      }
    }

    setMarks(
      flattenArray(receivedMessage.partitaCominciata.mappa.regioni.map(r => r.territori)).map(t => ({
        name: t.nome,
        value: t.numArmate,
        x: (t.x * 822.0 / 100.0) - 20,
        y: (t.y * 530.0 / 100.0) - 20,
        color: t.possessore.colore.toLowerCase(),
      }))
    );

    if (receivedMessage.azione.metodo == 'inizializza') {
      setUsers(receivedMessage.partitaCominciata.giocatori.map(g => {
        console.log(g);

        return ({
          name: g.nickname, color: g.colore.toLowerCase(), territories: 0, armies: 0
        })
      }));
      
    }
    else if (receivedMessage.azione.metodo == 'effettuaDisposizione') {
      
    }
    // You can update your component state or perform other actions here
  };

  // Initialize WebSocket connection
  useEffect(() => {
    const cb = async () => {
      fetch('http://127.0.0.1:3000/partita/partita-cominciata/wss/' + codice)
        .then(res => res.json())
        .then(url => {
          console.log("url", url)
          console.log("once");

          if (window.ws) {
            // window.lastWsListener && socket && socket.removeEventListener('message', window.lastWsListener);
            // window.lastWsListener && socket && socket.addEventListener('message', handleIncomingMessage);
            return;
          }
          
          // const ws = new WebSocket('wss://your-websocket-server-url');
          const newSocket = new WebSocket(url.url); // Replace with your WebSocket URL
          
          // Set up event listeners
          newSocket.addEventListener('open', () => {
            console.log('WebSocket connection opened');
          });
          
          newSocket.addEventListener('message', handleIncomingMessage);
          window.lastWsListener = handleIncomingMessage;
      
          newSocket.addEventListener('close', (event) => {
            console.log('WebSocket connection closed:', event.code, event.reason);
          });
      
          setSocket(newSocket);
          window.ws = newSocket;
        });
      }
      
      cb();
  }, [/*users, marks, setUsers, setMarks*/]); // Empty dependency array ensures that the effect runs once on component mount

  // Function to send a message through the WebSocket
  const sendMessage = (message) => {
    if (socket) {
      const payload = { ...message };
      socket.send(JSON.stringify(payload));
    }
  };

	const [timer, setTimer] = useState(300); // 300 seconds (5 minutes)
  const [selectedMark, setSelectedMark] = useState(null);

  const [selectedAttackMark, setSelectedAttackMark] = useState(null);
  const [selectedDefenseMark, setSelectedDefenseMark] = useState(null);
  const [showAttackPhaseModal, setShowAttackPhaseModal] = useState(false);

  const [startMark, setStartMark] = useState(null);
  const [targetMark, setTargetMark] = useState(null);
  const [showMovementPhaseModal, setShowMovementPhaseModal] = useState(false);
  
	const [showCombinationModal, setShowCombinationModal] = useState(false);
  const [showDispositionModal, setShowDispositionModal] = useState(false);

	const getCentralText = () => {
    return gameState === 'not my turn' ? 'Not My Turn' : phases[currentPhaseIndex];
  };

	const handleNextPhase = () => {
    const metodo = currentPhaseIndex == phases.length - 1
      ? 'terminaTurno'
      : 'passaAllaProssimaFase';
    sendMessage({
      metodo: metodo,
      parametri: {
        giocatore: {
          nickname: nickname // TODO
        },
      }
    })
    setTimer(300); // Reset the timer to 5 minutes

    /*
    if (currentPhaseIndex < phases.length - 1) {
      setCurrentPhaseIndex(currentPhaseIndex + 1);
      setGameState(phases[currentPhaseIndex + 1]);
      setTimer(300); // Reset the timer to 5 minutes
    } else {
      // Handle the end of the phases, for example, go back to the first phase
      setCurrentPhaseIndex(0);
      setGameState(phases[0]);
    }
    */
  };

	const handleOpenCombinationModal = () => {
    setShowCombinationModal(true);
  };

  const handleCloseCombinationModal = () => {
    setShowCombinationModal(false);
  };

	const handleMarkClick = (mark) => {
    if (gameState === 'disposition phase') {
      setSelectedMark(mark);
      setShowDispositionModal(true);
    } else if (gameState === 'attack phase') {
      setSelectedMark(mark);
      setShowAttackPhaseModal(true);
    } else if (gameState === 'movement phase') {
      setSelectedMark(mark);
      setShowMovementPhaseModal(true);
    }
  };

  const handleDispositionConfirm = (value) => {
    if (selectedMark) {
      sendMessage({
        metodo: 'effettuaDisposizione',
        parametri: {
          giocatore: {
            nickname: nickname // TODO
          },
          disposizione: {
            numeroArmate: value,
            nomeTerritorio: selectedMark.name,
          }
        }
      })

      // TODO to remove
      setRemainingArmates(remainingArmates - value);
    }
  };
  
  const onDeselectMarks = () => {
    setSelectedAttackMark(null);
    setSelectedDefenseMark(null);
  };
  
  const handleAttackPhaseModalClose = () => {
    setShowAttackPhaseModal(false);
  };

  const onSelectStartMark = () => {
    setStartMark(selectedMark);
  };
  
  const onSelectTargetMark = () => {
    setTargetMark(selectedMark);
  };
  
  
  const handleMovementPhaseModalClose = () => {
    setShowMovementPhaseModal(false);
  };

  useEffect(() => {
    let interval;
    if (gameState !== 'not my turn' && timer > 0) {
      interval = setInterval(() => {
        setTimer(timer - 1);
      }, 1000);
    }

    return () => clearInterval(interval);
  }, [gameState, timer]);

  useEffect(() => {
    if (gameState === 'disposition phase') {
      setRemainingArmates(12);
    }
  }, [gameState]);
  

  return (
    <div className="w-screen h-screen bg-sky-950 p-8 grid grid-cols-9 grid-rows-8">
      {/* First Row (100px fixed height) */}
      <div className="col-span-2 row-span-1 h-8">
        {/* "RISIKO" heading */}
        <h2 className="text-white text-2xl font-bold">RISIKO</h2>
      </div>

      <div className="col-span-5 row-span-1 flex items-center justify-center h-8">
        {/* Centered text in an h2 */}
        <h2 className="text-white text-center text-2xl font-bold">{getCentralText()}</h2>
      </div>

      <div className="col-span-2 row-span-1 flex items-center justify-end h-8">
        {/* Red "ABBANDONA" button aligned to the right */}
        <button className="bg-red-500 text-white p-2 rounded-full hover:bg-red-600">
          ABBANDONA
        </button>
      </div>

      {/* Second Row (remaining space) */}
			{/* First Column (ListOfActions) */}
			<div className="col-span-2 row-span-5">
				<ListOfActions actions={actions} />
			</div>

			{/* Second Column (Map) */}
			<div className="col-span-5 row-span-5">
				<Map marks={marks} onMarkClick={handleMarkClick} />
			</div>

			{/* Third Column (Chat and ListOfUsers) */ }
			<div className="col-span-2 row-span-3">
				<Chat />
			</div>

			<div className="col-span-2 row-span-2">
				<ListOfUsers users={users} />
			</div>

			{/* Last row */}
				{/* Two buttons */}
				<div className="col-span-1 row-span-2">
					<div className="col-span-1 row-span-1 pt-2">
						<button className="bg-blue-500 text-white p-2 rounded-full hover:bg-blue-600">
							Obiettivo
						</button>
					</div>
					<div className="col-span-1 row-span-1 pt-2">
						<button className="bg-blue-500 text-white p-2 rounded-full hover:bg-blue-600">
							Carte combinazione
						</button>
					</div>
				</div>

				{/* Other buttons */}
				<div className="col-span-5 row-span-2 flex items-center justify-end">
					
					<div className='flex flex-col items-center justify-center'>
						{ gameState !== 'not my turn' && (
							<div className="text-white text-xl">
								{Math.floor(timer / 60)}:{timer % 60 < 10 ? '0' : ''}{timer % 60}
							</div>
						)}
						<div className="col-span-1 row-span-1">
							{gameState === 'combination phase' && (
								<button
									onClick={handleOpenCombinationModal}
									className="bg-blue-500 text-white p-2 rounded-full hover:bg-blue-600"
								>
									Scegli combinazione
								</button>
							)}
							{gameState === 'disposition phase' && (
								<div className="text-white text-xl">
									Available Armates: {remainingArmates}
								</div>
							)}
							{gameState !== 'not my turn' && (
								<button
									className="bg-blue-500 text-white py-2 px-12 rounded-full hover:bg-blue-600"
									onClick={handleNextPhase}
								>
									Next
								</button>
							)}
						</div>
					</div>
				</div>

				{showCombinationModal && (
					<ScegliCombinazioneModal onClose={handleCloseCombinationModal} />
				)}

        {showDispositionModal && (
          <DispositionModal
            isOpen={showDispositionModal}
            onClose={() => setShowDispositionModal(false)}
            selectedMark={selectedMark}
            remainingArmates={remainingArmates}
            onConfirm={handleDispositionConfirm}
          />
        )}

        {gameState === 'attack phase' && (
          <AttackPhaseModal
            isOpen={showAttackPhaseModal}
            selectedMark={selectedMark}
            selectedAttackMark={selectedAttackMark}
            selectedDefenseMark={selectedDefenseMark}
            onSelectAttackMark={() => setSelectedAttackMark(selectedMark)}
            onSelectDefenseMark={() => setSelectedDefenseMark(selectedMark)}
            onDeselectMarks={onDeselectMarks}
            onClose={handleAttackPhaseModalClose}
          />
        )}

        {gameState === 'movement phase' && (
          <MovementPhaseModal
            isOpen={showMovementPhaseModal}
            startMark={startMark}
            targetMark={targetMark}
            onSelectStartMark={onSelectStartMark}
            onSelectTargetMark={onSelectTargetMark}
            onDeselectMarks={onDeselectMarks}
            onClose={handleMovementPhaseModalClose}
          />
        )}

			</div>
  );
};

export default Gioco;
