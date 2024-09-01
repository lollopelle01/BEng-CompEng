'use strict';

class App extends React.Component {
    constructor() {
        super();
        this.state = {
            numLanci: 0,
            somme: [],
            values: [],
            okList: [],
            flopList: []
        }

        this.setter = this.setter.bind(this);
        this.play = this.play.bind(this);
        this.reset = this.reset.bind(this);
    }

    setter({da1, da2, da3}) {
    
        var val = [];
        val.push(da1.value);
        val.push(da2.value);
        val.push(da3.value);

        var totVal = this.state.values;
        totVal.push(val);


        this.setState({
            values: totVal,
            numLanci: this.state.numLanci+1,
        });

        this.play();

    }
    
    play() {

        var somma = parseInt(this.state.values[this.state.values.length-1][0]) +
                    parseInt(this.state.values[this.state.values.length-1][1]) +
                    parseInt(this.state.values[this.state.values.length-1][2]);

        var totVal = this.state.okList;
        totVal.push(this.state.values[this.state.values.length-1]);

        if(somma > 6 && somma < 15) {
            this.setState({
                okList: totVal
                
            });
        } else {

            totVal = this.state.flopList;
            totVal.push(this.state.values[this.state.values.length-1]);

            this.setState({
                flopList: totVal
            });
        }
    }

    reset() {
       
        var el = document.getElementsByClassName("dadi");
        for(var i = 0; i < el.length; i++) {
            el[i].value = "";
            el[i].innerHTML = "";
            $("#d"+(i+1)).css({
                "background-color" : "white"
            });
        }

        el = document.getElementsByClassName("stat");
        for(var i = 0; i < el.length; i++) {
            el[i].value = "";
            el[i].innerHTML = "";
        }

        document.getElementById("visual").innerHTML = "";

        this.setState({
            numLanci: 0,
            somme: [],
            values: [],
            okList: [],
            flopList: []
        });

    }


    render() {
        return (
            <div>
                <Config setter={this.setter}></Config>
                <br/><hr/>
                <Visual okList={this.state.okList} numLanci={this.state.numLanci}></Visual>
                <br/><hr/>
                <Statistic okList={this.state.okList} flopList={this.state.flopList} reset={this.reset}></Statistic>
            </div>
        );
    }



}