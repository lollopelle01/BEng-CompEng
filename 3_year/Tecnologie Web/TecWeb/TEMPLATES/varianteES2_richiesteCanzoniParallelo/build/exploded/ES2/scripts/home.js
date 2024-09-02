document.addEventListener("DOMContentLoaded", function() {
	let numC_input = document.getElementById("numC")

	numC_input.addEventListener("input", function() {
		if (isNaN(numC_input.value)) {
			return;
		}

		let numC = parseInt(numC_input.value);
		if (numC != 3 && numC != 4) {
			return;
		}

		download(numC);
	})
})

function download(c) {
	for (let i = 0; i < c; i++) {
		xhr_req(c, i, agg_textarea)
	}
}

function agg_textarea(o) {
	document.getElementById("output").value += o.message.nome + "\n"
}

function xhr_req(c, i, callback) {
	let xhr = new XMLHttpRequest();
	xhr.addEventListener("readystatechange", function(e) {
		if (e.target.readyState == XMLHttpRequest.DONE && e.target.status == 200) {
			resp = JSON.parse(e.target.responseText)
			if (resp.type == "canzone") {
				callback(resp)
				xhr_req(c, c + i, callback)
			} else if (resp.type == "canzone-bloccata") {
				xhr_req(c, c + i, callback)
			}
		}
	})
	xhr.open("POST", "servlet")
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")

	xhr.send(`i=${i}`)
}