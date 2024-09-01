class Inserimento extends React.Component {
    constructor(props) {
        super(props);

        this.generatePiste = this.generatePiste.bind(this);
    }

    generatePiste(){
        let num_gare = $("#num_gare").val(); console.log(num_gare);

        if(isNaN(num_gare) || parseInt(num_gare) < 5){
            $("#num_gare").val("");
            return;
        }

        if(num_gare == ""){return;}

        this.props.generate({});
    }

    render() {
        return (
            <div>
                <label>Numero di gare</label>
                <input type="text" id="num_gare" placeholder="num > 0" onBlur={this.generatePiste}></input>
        </div>
        );
    }
}
