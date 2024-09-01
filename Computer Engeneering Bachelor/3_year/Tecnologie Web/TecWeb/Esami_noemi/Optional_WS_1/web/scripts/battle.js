var socket = new WebSocket("ws://localhost:8080/98_Optional_ws_1/server");
function send( data) {
    var json = JSON.stringify(data);
    socket.send(json);
}

let clientId;
let should_answer = true;

const hit_request_actions_element = document.getElementById("hit_request_actions");
const client_id_element = document.getElementById("client_id_field");
const hit_question_element = document.getElementById("hit_question");
const hit_result_element = document.getElementById("hit_results");

socket.onmessage =  function (event){
	const message = JSON.parse(event.data);
	if(message.type == "client_id_assignment"){
		clientId = message.content;
		client_id_element.innerHTML += clientId;
	}
	if(message.type == "hit_server_result_request"){
		should_answer && (hit_request_actions_element.style.display = "block");
		hit_question_element.innerHTML = message.content + ". Ti ha colpito?";
	}
 	if(message.type == "hit_user_result"){
		hit_result_element.innerHTML += ("<p>"+message.content+"</p>");
	}
	if(message.type == "fine_turno"){
		hit_result_element.innerHTML += ("<p>"+message.content+"</p>");
		should_answer = true;
	}
	if(message.type == "error"){
		alert("Errore: " + message.content);
	}
}

function sendHitRequestFunction(){
	const row_field_value = document.getElementById("row_field").value; //letter
	const col_field_value = document.getElementById("col_field").value; //number
	if(row_field_value.length == 0 || col_field_value.length == 0){
		alert("uno dei due operandi non è un valido");
		return;
	}

	const hitReq = {};
	hitReq.type = "hit_user_request";
	hitReq.row = row_field_value;
	hitReq.column = col_field_value;
	
	send(hitReq);
	
	hit_request_actions_element.style.display = "none";
}

function sendHitResponseFunction(hit_response=false){
	if(!hit_response){
		alert("Errore inaspettato");
		return;
	}

	var hitResp = {};
	hitResp.type = "hit_user_result_request";
	hitResp.content = hit_response;
	
	send(hitResp);
	should_answer = false;
	hit_request_actions_element.style.display = "none";
}

