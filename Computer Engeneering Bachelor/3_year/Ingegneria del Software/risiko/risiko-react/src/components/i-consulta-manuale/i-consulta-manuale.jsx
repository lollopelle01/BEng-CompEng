import React from "react";
import closedRules from '../../assets/book-svgrepo-com.svg';
import opneRules from '../../assets/book-open-svgrepo-com.svg';
import './i-consulta-manuale.css'

const Footer = () => {

    return (
        <footer>
            <div class="images">
                <img src={closedRules} className="closed-rules"/>
                <img src={opneRules} className="opened-rules"/>
            </div>
            <div>Regole</div>
        </footer>
    );
}

export default Footer;