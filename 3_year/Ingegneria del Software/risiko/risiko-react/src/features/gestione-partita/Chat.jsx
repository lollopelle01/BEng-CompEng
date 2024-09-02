import React, { useState, useEffect } from 'react';

const Chat = () => {
  const [messages, setMessages] = useState([]);
  const [message, setMessage] = useState('');
  const [socket, setSocket] = useState(null);

  // Update the state with a new message
  const addMessage = (message) => {
    setMessages([...messages, message]);
  };

  // Send a message to the WebSocket server
  const sendMessage = () => {
    if (socket) {
      socket.send(message);
      setMessage('');
    }
  };

  useEffect(() => {
    // Initialize a WebSocket connection
    const ws = new WebSocket('wss://your-websocket-server-url');

    ws.onopen = () => {
      // WebSocket is connected
      setSocket(ws);
      addMessage('Connected to the chat.');
    };

    ws.onmessage = (event) => {
      // Received a new message from the server
      addMessage(event.data);
    };

    ws.onclose = () => {
      // WebSocket connection is closed
      addMessage('Connection closed.');
    };

    return () => {
      // Clean up WebSocket connection when the component unmounts
      ws.close();
    };
  }, []);

  return (
    <div className='pb-8 h-full w-full'>
			<div className="rounded bg-sky-800 p-4 h-full w-full">
      <h4 className="text-white text-lg font-bold mb-2">Chat</h4>
      <ul className="text-white">
        {messages.map((msg, index) => (
          <li key={index}>{msg}</li>
        ))}
      </ul>
      <div className="mt-4 flex">
        <input
          type="text"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          className="p-2 border border-gray-300 rounded-l w-full"
        />
        <button
          onClick={sendMessage}
          className="bg-blue-500 text-white p-2 rounded-r hover:bg-blue-600"
        >
          Send
        </button>
      </div>
    </div>
		</div>
  );
};

export default Chat;
