// ListOfActions.js
import React from 'react';

const ListOfActions = ({ actions }) => {
  return (
    <div className="rounded bg-sky-800 p-4 w-full h-full">
      <h4 className="text-white text-lg font-bold mb-2">Actions</h4>
      <ul className="text-white">
        {actions.map((action, index) => (
          <li key={action + index}>{action}</li>
        ))}
      </ul>
    </div>
  );
};

export default ListOfActions;
