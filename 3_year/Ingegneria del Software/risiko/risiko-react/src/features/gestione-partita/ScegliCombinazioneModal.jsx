import React, { useState } from 'react';

const ScegliCombinazioneModal = ({ onClose }) => {
  // Example card and triplet data
  const cards = ['Card 1', 'Card 2', 'Card 3', 'Card 4', 'Card 5'];
  const triplets = [
    ['Card 1', 'Card 2', 'Card 3'],
    ['Card 3', 'Card 4', 'Card 5'],
    // Add more triplets as needed
  ];

  const [selectedTriplet, setSelectedTriplet] = useState(null);

  const handleSelectTriplet = (triplet) => {
    setSelectedTriplet(triplet);
    console.log('Selected Triplet:', selectedTriplet);
    onClose();
  };

  const handleConfirm = () => {
    // Close the modal
    onClose();
  };

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-75">
      <div className="bg-white p-4 rounded shadow-lg">
        <div className="mb-4">
          <h2 className="text-xl font-bold">Scegli una combinazione</h2>
        </div>
        <div className="overflow-x-auto">
          <div className="flex space-x-4">
            {cards.map((card, index) => (
              <div key={index} className="border p-2 rounded">
                {card}
              </div>
            ))}
          </div>
        </div>
        <div className="mt-4">
          {triplets.map((triplet, index) => (
            <div key={index} className="flex items-center space-x-4">
              <div className="flex space-x-2">
                {triplet.map((card, cardIndex) => (
                  <div key={cardIndex} className="border p-2 rounded">
                    {card}
                  </div>
                ))}
              </div>
              <button
                onClick={() => handleSelectTriplet(triplet)}
                className={`bg-blue-500 text-white p-2 rounded hover:bg-blue-600 ${
                  selectedTriplet === triplet ? 'bg-green-500' : ''
                }`}
              >
                Select
              </button>
            </div>
          ))}
        </div>
        <div className="mt-4">
          <button
            onClick={handleConfirm}
            className="bg-blue-500 text-white p-2 rounded hover:bg-blue-600"
          >
            Conferma
          </button>
        </div>
      </div>
    </div>
  );
};

export default ScegliCombinazioneModal;
