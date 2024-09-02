'use strict';

function Conteggio(props) {
    return(<table className="grid">
        <tr>
            <th className="counter-cellv">Tentativi</th>
            <th className="counter-cells">{props.tentativi}</th>
        </tr>
    </table>)
}