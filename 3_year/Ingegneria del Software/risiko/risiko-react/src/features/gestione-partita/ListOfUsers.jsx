import React from 'react';

const ListOfUsers = ({ users }) => {
  return (
    <div className="rounded bg-sky-800 p-4 h-full w-full">
      <h4 className="text-white text-lg font-bold mb-2">List of Users</h4>
      <ul className="text-white">
        {users.map((user, index) => (
          <li key={index} className="mb-2 p-2 rounded bg-white flex items-center">
            <div
              style={{
                backgroundColor: user.color,
                width: '12px',
                height: '12px',
                borderRadius: '50%',
                marginRight: '8px',
              }}
            />
            <span className="text-black">{user.name} - Territories: {user.territories} - Armies: {user.armies}</span>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ListOfUsers;
