'use strict';

class App extends React.Component {
    constructor() {
        super();
        this.state = {
            h : 0,
            l : 0,
            grid : [],
            tentativi : 0,
            vittorie : 0,
            sconfitte : 0
        }

        this.generate = this.generate.bind(this);
        this.play = this.play.bind(this);
    }

    generate({h, l, result}) {
        let win = this.state.vittorie;
        let lost = this.state.sconfitte;
        if(result != undefined) {
            if(result == "win") {
                win++;
            } else {
                lost++;
            }
        }

        // Creo griglia vuota
        let matrix = new Array(h);
        for(let i = 0; i < h; i++) {
            matrix[i] = new Array(l);
        }
        
        // Inserisco mine
        for(let i = 0; i < h; i++) {
        	let mine_riga=0;
        	
        	// Metto due mine casuali in ogni riga
        	while(mine_riga < 2){ // Finchè non le metto continuo --> potrebbe mettermi solo degli 0
        		
                for(let j = 0; j < l; j++) {
                    if(mine_riga < 2) {
                        matrix[i][j] = Math.floor(Math.random()*2); // 1 la mette		 0 non la mette
                        if(matrix[i][j] == 1){
                        	mine_riga++;
                        }
                    } else {
                        matrix[i][j] = 0;
                    }
                }
        	}
        	
        	
        }
        
        console.log("Nuova matrice generata");
        console.log(matrix)
        
        // Salvo la griglia appena creata
        this.setState({
	    	h : h,
	        l : l,
            grid : matrix,
            tentativi : 0,
            vittorie : win,
            sconfitte : lost
        })
    }

    play(e) {
        let flower = e.target.id;
        
        // A priori inserisco nella cella in numero cardinae del tentativo
        $(e.target).text(this.state.tentativi + 1);

        if(flower == 1) {
            $(e.target).css({
                "background-color" : "red"
            });
            
            if(this.state.tentativi == 3){
            	alert("Partita terminata: hai perso!");
            	this.generate({h : 0, l : 0});
                this.generate({h : this.state.h, l : this.state.l, result : "lose"});
            }
            else {
	            this.setState({
	                tentativi : this.state.tentativi + 1
	            });
            }
        } else {
            $(e.target).css({
                "background-color" : "blue"
            });

            if(this.state.tentativi == 5) {
                alert("Partita terminata: hai vinto!");
                this.generate({h : 0, l : 0});
                this.generate({h : this.state.h, l : this.state.l, result : "win"});
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

