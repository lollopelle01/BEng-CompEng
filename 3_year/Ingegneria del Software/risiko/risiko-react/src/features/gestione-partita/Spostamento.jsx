import React from 'react';

const MovementPhaseModal = ({
  isOpen,
  startMark,
  targetMark,
  onSelectStartMark,
  onSelectTargetMark,
  onDeselectMarks,
  onClose,
}) => {
  return (
    isOpen && (
      <div className="fixed inset-0 flex items-center justify-center z-50">
        <div className="modal-container bg-white w-11/12 md:max-w-md mx-auto rounded shadow-lg z-50 overflow-y-auto">
          <div className="modal-content py-4 text-left px-6">
            <h2 className="text-xl font-semibold mb-4">Movement Phase</h2>

            <div className="mb-4">
              <strong>Select a Start Mark:</strong>
              {startMark ? (
                <div>
                  <p>Selected: {startMark.name}</p>
                  <button onClick={() => onSelectStartMark(null)}>Deselect</button>
                </div>
              ) : (
                <button onClick={onSelectStartMark}>Select</button>
              )}
            </div>

            <div className="mb-4">
              <strong>Select a Target Mark:</strong>
              {targetMark ? (
                <div>
                  <p>Selected: {targetMark.name}</p>
                  <button onClick={() => onSelectTargetMark(null)}>Deselect</button>
                </div>
              ) : (
                <button onClick={onSelectTargetMark}>Select</button>
              )}
            </div>

            <div className="mb-4">
              {startMark && targetMark ? (
                <div>
                  <p>
                    <strong>Both marks are selected. To change selections:</strong>
                  </p>
                  <button onClick={onDeselectMarks}>Deselect Both</button>
                </div>
              ) : (
                <p>
                  <strong>To make a movement, select both a Start and Target Mark.</strong>
                </p>
              )}
            </div>

            <div>
              <button onClick={onClose}>Close</button>
            </div>
          </div>
        </div>
      </div>
    )
  );
};

export default MovementPhaseModal;
