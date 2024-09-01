'use strict';

class App extends React.Component {
  constructor(){
        super();
        this.state = {
        larg : 0,
        lung : 0,
        matrix : "",
        tesoro : "",
        tentativi : 0,
        punteggio : 0
        }
        this.validate = this.validate.bind(this);
        this.onClick = this.onClick.bind(this); 
  }
  punteggio(){
    var tentativi = this.state.tentativi;
    var punteggio = 0;
    if(tentativi<=10){
        punteggio = 10;
    } else {
        punteggio = 5;
    }
    this.setState({
        punteggio : punteggio
    })
    alert("gioco terminato, refresha la pagina");
  }

  onClick (e){
    var id = e.target.id;
    document.getElementById(id).setAttribute("disabled","disabled");
    this.setState({
        tentativi : this.state.tentativi +1
    })
    if(id==this.state.tesoro){
        document.getElementById(id).style.backgroundColor = "blue";
        document.getElementById(id).value = "T";
        this.punteggio();

    } else {
        document.getElementById(id).style.backgroundColor = "yellow";
    }
  }

  generaMatrix(larg,lung){
    var matrix = [];
    var count=0;

    for(var row=0; row<larg; row++){
        for(var col=0; col<lung; col++){
            matrix.push(<input id={count} size='1' onClick={this.onClick}></input>)
            count++;
        }
        matrix
    }
    this.setState({
        matrix : matrix,
        tesoro : Math.floor(Math.random() * count)
    })
  }

  validate(){
    var larg = document.getElementById("larg").value;
    var lung = document.getElementById("lung").value;

    if(larg!== "" && lung!=="" && larg>5 && lung>5){
        this.setState({
            larg: larg,
            lung: lung
        })
        this.generaMatrix(larg, lung);
    }
  }



  reset(){
      this.setState({
          result: "",
          input: ""
      })
        };

  render() {
      return (

        <div className="calculator-body">
            <h1>Servizio</h1>
            <Configurazione validate={this.validate}/>
            <Mappa matrix={this.state.matrix}/>
            <Punteggio tentativi={this.state.tentativi} punteggio={this.state.punteggio}/>
        </div>

      );
  }
}
