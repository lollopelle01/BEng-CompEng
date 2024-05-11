'use strict';

class App extends React.Component {
    constructor() {
        super();
        this.state = {
            size : 0,
            mines : 0,
            grid : [],
            tentativi : 0,
            vittorie : 0,
            sconfitte : 0
        }

        this.generate = this.generate.bind(this);
        this.play = this.play.bind(this);
    }

    generate({mines, size, result}) {
        let win = this.state.vittorie;
        let lost = this.state.sconfitte;
        if(result != undefined) {
            if(result == "win") {
                win++;
            } else {
                lost++;
            }
        }

        let total_mines = mines;
        let inserted_mines = 0;
        let matrix = new Array(size);
        for(let i = 0; i < size; i++) {
            matrix[i] = new Array(size);
        }

        //Potrebbe inserire meno fiori del previsto
        for(let i = 0; i < size; i++) {
            for(let j = 0; j < size; j++) {
                if(inserted_mines < total_mines) {
                    matrix[i][j] = Math.floor(Math.random()*2);
                    if(matrix[i][j] == 1){
                        inserted_mines++;
                    }
                } else {
                    matrix[i][j] = 0;
                }
            }
        }
        //cosi risolviamo il problema delle mine inferiori
        while(inserted_mines<total_mines){

            for(let i = 0; i < size; i++) {
                for(let j = 0; j < size; j++) {
                    if(inserted_mines < total_mines && matrix[i][j]==0) {
                        matrix[i][j] = Math.floor(Math.random()*2);
                        if(matrix[i][j] == 1){
                            inserted_mines++;
                        }
                    }
                }
            }
        }
        
        console.log("Nuova matrice generata");
        console.log(matrix)
        $(".cell").each(function() {
            $(this).css({
                "background-color" : "yellow"
            })
        })
        this.setState({
            grid : matrix,
            mines : mines,
            size : size, 
            tentativi : 0,
            vittorie : win,
            sconfitte : lost
        })
    }

    play(e) {
        let flower = e.target.id;

        if(flower == 1) {
            $(e.target).css({
                "background-color" : "red"
            });
            alert("Game Over!");
            this.generate({mines : this.state.mines, size : this.state.size, result : "lose"});
        } else {
            $(e.target).css({
                "background-color" : "green"
            });

            if(this.state.tentativi == 4) {
                alert("You win!");
                this.generate({mines : this.state.mines, size : this.state.size, result : "win"});
            } else {
                this.setState({
                    tentativi : this.state.tentativi + 1
                });
            }
        }
    }

    render() {
        return (
            <div>
                <Configurazione generate={this.generate}/>
                <Gioco grid={this.state.grid} play={this.play}/>
                <Conteggio vittorie={this.state.vittorie} sconfitte={this.state.sconfitte}/>
            </div>
        );
    }
}

