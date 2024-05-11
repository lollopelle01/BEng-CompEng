'use strict';

function Visual(props) {
   return(
        <div id="visual">
            <table id="stat">
                <tr>
                    <td>Tentativi:</td>
                    <td>Punteggio:</td>
                </tr>
                <tr>
                    <td>{props.tentativi}</td>
                    <td>{props.score}</td>
                </tr>
            </table>
        </div>
    )
}