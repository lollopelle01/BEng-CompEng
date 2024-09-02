// Map.js
import React from 'react';
import mapImage from '../../assets/map.png';
const Map = ({ marks, onMarkClick }) => {
  return (
    <div className="px-8 w-full h-full">
      <div
        className="relative"
        style={{ backgroundImage: `url(${mapImage})`, backgroundSize: 'cover', width: 822, height: 530 }}
      >
        {marks.map((mark, index) => (
          <div
            key={index}
            className="absolute text-white text-center cursor-pointer"
            style={{
              left: `${mark.x}px`,
              top: `${mark.y}px`,
              border: `4px solid ${mark.color}`,
              borderRadius: '50%',
              width: '40px',
              height: '40px',
              lineHeight: '30px',
              backgroundColor: 'white',
              color: 'black'
            }}
            onClick={() => onMarkClick(mark)}
          >
            {mark.value}
          </div>
        ))}
      </div>
    </div>
  );
};

export default Map;
