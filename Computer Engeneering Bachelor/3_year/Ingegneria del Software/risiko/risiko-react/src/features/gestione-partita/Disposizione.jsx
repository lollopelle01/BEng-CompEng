import React, { useState } from 'react';

const DispositionModal = ({ isOpen, onClose, remainingArmates, onConfirm, selectedMark }) => {
  const [armatesToPlace, setArmatesToPlace] = useState(0);

  const handleConfirm = () => {
    if (armatesToPlace <= remainingArmates) {
      onConfirm(armatesToPlace);
      onClose();
    } else {
      // Add error handling for invalid input (e.g., show a message)
    }
  };

  return (
    isOpen && (
      <div className="fixed inset-0 flex items-center justify-center z-50">
        <div className="modal-container bg-white w-11/12 md:max-w-md mx-auto rounded shadow-lg z-50 overflow-y-auto">
          <div className="modal-content py-4 text-left px-6">
            <h2 className="text-xl font-semibold mb-2">Place Armates in {selectedMark.name}</h2>
            <input
              type="number"
              value={armatesToPlace}
              onChange={(e) => setArmatesToPlace(parseInt(e.target.value, 10))}
              className="w-full p-2 border border-gray-300 rounded mb-4"
            />
            <button
              onClick={handleConfirm}
              className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600"
            >
              Confirm
            </button>
          </div>
        </div>
      </div>
    )
  );
};

export default DispositionModal;
