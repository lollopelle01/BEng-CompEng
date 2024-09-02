import React, { useState } from 'react';
import Modal from '../shared/Modal';
import RicercaModal from './RicercaModal';
import CreaModal from './CreaModal';
import UnioneModal from './UnioneModal';

const Accesso = ({ nickname, onClose, isAccessoOpen }) => {
  const [isRicercaOpen, setIsRicercaOpen] = useState(false);
  const [isCreaOpen, setIsCreaOpen] = useState(false);
  const [isUnioneOpen, setIsUnioneOpen] = useState(false);

  return (
    <div className="bg-white w-64 p-4 rounded-lg">
      <h2 className="text-2xl font-bold mb-4">Accesso</h2>
      <button
        onClick={() => setIsRicercaOpen(true)}
        className="p-2 m-2 bg-blue-500 text-white rounded-full hover:bg-blue-600"
      >
        Ricerca
      </button>
      <button
        onClick={() => setIsCreaOpen(true)}
        className="p-2 m-2 bg-blue-500 text-white rounded-full hover:bg-blue-600"
      >
        Crea
      </button>
      <button
        onClick={() => setIsUnioneOpen(true)}
        className="p-2 m-2 bg-blue-500 text-white rounded-full hover:bg-blue-600"
      >
        Unione
      </button>
      <button
        onClick={onClose}
        className="p-2 bg-red-500 text-white rounded-full hover:bg-red-600"
      >
        Close
      </button>

      <Modal isOpen={isRicercaOpen && isAccessoOpen} onClose={() => setIsRicercaOpen(false)}>
        <RicercaModal isOpen={isRicercaOpen && isAccessoOpen} nickname={nickname} onClose={() => setIsRicercaOpen(false)} />
      </Modal>

      <Modal isOpen={isCreaOpen && isAccessoOpen} onClose={() => setIsCreaOpen(false)}>
        <CreaModal isOpen={isCreaOpen && isAccessoOpen} nickname={nickname} onClose={() => setIsCreaOpen(false)} />
      </Modal>

      <Modal isOpen={isUnioneOpen && isAccessoOpen} onClose={() => setIsUnioneOpen(false)}>
        <UnioneModal isOpen={isUnioneOpen && isAccessoOpen} nickname={nickname} onClose={() => setIsUnioneOpen(false)} />
      </Modal>
    </div>
  );
};

export default Accesso;
