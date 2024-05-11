'use strict';

class Percorso extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        let caselle = [];
        let pos = this.props.pos;
        let car = this.props.car;
        for(let i = 0; i <= 10; i++){
            caselle.push(<Casella index={i} car={car}/>)
        }

        if(pos == 0) {
            caselle[0] = <Casella index={"CAR"} car={car}/>
        } else if(pos >= 10) {
            caselle[10] = <Casella index={"CAR"} car={car}/>
        } else {
            caselle[pos] = <Casella index={"CAR"} car={car}/>
        }

        return (<tr>
            {caselle.map(c => c)}
        </tr>);
    }
    
}