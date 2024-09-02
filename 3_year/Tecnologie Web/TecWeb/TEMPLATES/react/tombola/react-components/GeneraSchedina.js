'use strict';

class GeneraSchedina extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            numbers : []
        }

        this.numberInput = this.numberInput.bind(this);
        this.submit = this.submit.bind(this); 
    }

    numberInput(e) {
        let number = e.target.value;
        let index = e.target.id;
        if(isNaN(number) || parseInt(number) > 10 || parseInt(number) < 1 || this.state.numbers.includes(number)) {
            e.target.value = "";
            console.log("Non immbissibile " + number);
        }

        this.state.numbers[index] = number;
        console.log(this.state.numbers);
    }

    submit(e) {
        this.props.generate({numbers : this.state.numbers});
    }
    

    render() {
        return(<div className="genera-div">
            <button id="genera-btn" onClick={function() {
                $("#schedina-form").show();
                $("#genera-btn").hide();
            }}>GENERA</button>
            <div id="schedina-form" hidden>
                <label>Inserisci cinque numeri da uno a dieci</label> <br></br>
                <input type="text" className="numeroSchedina" name="numero" id="0" onChange={this.numberInput}></input>
                <input type="text" className="numeroSchedina" name="numero" id="1" onChange={this.numberInput}></input>
                <input type="text" className="numeroSchedina" name="numero" id="2" onChange={this.numberInput}></input>
                <input type="text" className="numeroSchedina" name="numero" id="3" onChange={this.numberInput}></input>
                <input type="text" className="numeroSchedina" name="numero" id="4" onChange={this.numberInput}></input> <br></br>
                <button id="crea-btn" onClick={this.submit}>CREA SCHEDINA</button>
            </div>
        </div>);
    }
}
