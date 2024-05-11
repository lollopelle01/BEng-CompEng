'use strict';

class App extends React.Component {
    constructor() {
        super();
        this.state = {
            larghezza: 0,
            lunghezza: 0,
            grid: [],
            tentativi: 0,
            score: 0
        }

        this.setter = this.setter.bind(this);
        this.play = this.play.bind(this);
        this.reset = this.reset.bind(this);
    }

    setter({larg, lung}) {
        console.log(larg);
        console.log(lung);
        this.setState({
            larghezza: larg,
            lunghezza: lung
        })  
    }
    
    play(e) {

        if(this.state.tentativi == 0) {

            var bottoni = document.getElementsByName("bottone");
            var array = Array.prototype.slice.call(bottoni);

            for(var i = 0; i < bottoni.length; i++) {
                bottoni[i].value = 0;
            }
           
            var num = Math.floor(Math.random()*bottoni.length);
            bottoni[num].value = 1; //tesoro

            $("#Resetta").css({
                "display" : "block"
            });
        }

        e.preventDefault();
        //individuo il bottone, il suo id e il suo valore
        let button = e.target;
        let id = button.id;
        
        if(button.value == 1) {
            $(e.target).css({
                "background-color" : "blue"
            });
            alert("You Win!");

            button.innerHTML = 'T';

            if(this.state.tentativi < 10) {
                this.setState({
                    score : 5
                });
            } else {
                this.setState({
                    score : 2
                });
            }
        } else {
            $(e.target).css({
                "background-color" : "yellow"
            });
            this.setState({
                tentativi : this.state.tentativi + 1
            });
        }
    }

    reset() {
        var el = document.getElementsByName("bottone");
        for(var i = 0; i < el.length; i++) {
            if(el[i].value == 1) {
                el[i].innerHTML = "";
            }

            el[i].value = "";
        }

        $('button[name="bottone"]').css({
            "background-color" : "gray"
        });

        this.setState({
            tentativi : 0,
            score: 0
        });
    }

    render() {
        return (
            <div>
                <Config setter={this.setter}></Config>
                <br/><br/>
                <Grid columns={this.state.larghezza} rows={this.state.lunghezza} play={this.play} reset={this.reset}></Grid>
                <br/><br/>
                <Visual tentativi={this.state.tentativi} score={this.state.score}></Visual>
            </div>
        );
    }



}