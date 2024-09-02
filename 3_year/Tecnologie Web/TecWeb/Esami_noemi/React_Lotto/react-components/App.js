'use strict';

class App extends React.Component {
  constructor(){
        super();
        this.state = {
        messaggio:""
        }
        this.inizializza = this.inizializza.bind(this);
        this.valida = this.valida.bind(this);
        this.startEstrazione = this.startEstrazione.bind(this); 
  }

  check(num){
    var myNum = [];
    for(var i=0; i<5;i++){
        var item = document.getElementById("s"+i).value;
        if(num.includes(item)){
            myNum.push(item);
        }
    }
    var color = "";
    if(myNum.length==2){
      color = "yellow";
    }
    else if(myNum.length==3){
      color = "green";
    }
    else if(myNum.length==4){
      color = "blue";
    }
    else if(myNum.length==5){
      color = "red";
    }

    for(var i=0; i<5;i++){
      
      var myItem = document.getElementById("s"+i);
      if(myNum.includes(myItem.value)){
        myItem.style.backgroundColor = color;
      }
      
    }

    if(myNum.length>1){
      this.setState({
        messaggio: "hai vinto"
      })
    } else {
      this.setState({
        messaggio: "hai perso"
      })
    }
    
}

  startEstrazione(e){
    var nums = [];
    var count = 0;
    while(count!==5){
        var myNum = Math.floor(Math.random() * 10)+1;
        if(!nums.includes(myNum+"")){
            nums.push(myNum+"");
            document.getElementById("e"+count).value = myNum;
            count++;
        }
    }
    this.check(nums);
  }

  start(){
    for(var i=0; i<5; i++){
        var itemConf = document.getElementById("c"+i).value;
        var itemSchedina = document.getElementById("s"+i);
        itemSchedina.value = itemConf;
    }
    document.getElementById("startEstrazione").removeAttribute("hidden");
  }

  inizializza(e) {
        document.getElementById("schedina").removeAttribute("hidden");
        for(var i=0; i<5; i++){
          var itemConf = document.getElementById("c"+i).value;
          var itemSchedina = document.getElementById("s"+i);
          itemSchedina.value = itemConf;
      }
      for(var i=0; i<5; i++){
        var item = document.getElementById("s"+i);
        item.style.backgroundColor = "";
        item.value = "";
    }

  };

  valida(e) {
    var check = true;
    for(var i =0; i<5; i++){
        var item = document.getElementById("c"+i).value;
        if(item===null || item===""){
            check=false;
            break;
        }
    }
    if(check){
        this.start();
    }
};

  render() {
      return (

        <div className="calculator-body">
            <h1>Calcolatrice</h1>
            <Configurazione valida={this.valida} inizializza={this.inizializza}/>
            <Schedina/>
            <Estrazione startEstrazione={this.startEstrazione} messaggio={this.state.messaggio}/>
        </div>

      );
  }
}
