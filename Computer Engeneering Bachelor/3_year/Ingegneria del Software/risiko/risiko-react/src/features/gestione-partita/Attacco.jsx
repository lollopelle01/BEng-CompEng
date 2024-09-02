import React from 'react';

const AttackPhaseModal = ({
  isOpen,
  selectedAttackMark,
  selectedDefenseMark,
  onSelectAttackMark,
  onSelectDefenseMark,
  onDeselectMarks,
  onClose,
}) => {
  return (
    isOpen && (
      <div className="fixed inset-0 flex items-center justify-center z-50">
        <div className="modal-container bg-white w-11/12 md:max-w-md mx-auto rounded shadow-lg z-50 overflow-y-auto">
          <div className="modal-content py-4 text-left px-6">
            <h2 className="text-xl font-semibold mb-4">Attack Phase</h2>

            <div className="mb-4">
              <strong>Select an Attack Mark:</strong>
              {selectedAttackMark ? (<div>
                <p>Selected: {selectedAttackMark.name}</p>
                <button onClick={() => onSelectAttackMark(null)}>Deselect</button>
								</div>) : (
                <button onClick={onSelectAttackMark}>Select</button>
              )}
            </div>

            <div className="mb-4">
              <strong>Select a Defense Mark:</strong>
              {selectedDefenseMark ? (<div>
                <p>Selected: {selectedDefenseMark.name}</p>
                <button onClick={() => onSelectDefenseMark(null)}>Deselect</button>
							</div>) : (
                <button onClick={onSelectDefenseMark}>Select</button>
              )}
            </div>

            <div className="mb-4">
              {selectedAttackMark && selectedDefenseMark ? (<div>
                <p>
                  <strong>Both marks are selected. To change selections:</strong>
                </p>
                <button onClick={onDeselectMarks}>Deselect Both</button>
							</div>) : (
                <p>
                  <strong>To perform an attack, select both an Attack and Defense Mark.</strong>
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

export default AttackPhaseModal;
