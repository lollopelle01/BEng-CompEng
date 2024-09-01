'use strict';

function Conteggio(props) {
    return(<table className="grid">
        <tr>
            <th colspan="2" className="counter-cell">CONTEGGIO</th>
        </tr>
        <tr>
            <th className="counter-cellv">Vittorie</th>
            <th className="counter-cells">Sconfitte</th>
        </tr>
        <tr>
            <td className="counter-cellv">{props.vittorie}</td>
            <td className="counter-cells">{props.sconfitte}</td>
        </tr>
    </table>)
}