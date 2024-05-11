
const textArea = document.getElementById("text_area");
const fileName = document.getElementById("file_name");
const submitButton = document.getElementById("fantasma");

textArea.addEventListener("keyup", updateSubmitButtonVisibility);
fileName.addEventListener("keyup", updateSubmitButtonVisibility);

function updateSubmitButtonVisibility() {
  const textAreaValue = textArea.value;
  const fileNameValue = fileName.value;

  /* Controlla se entrambi i campi hanno dati validi */
  
  const isValidTextArea = textAreaValue.length >= 10 && textAreaValue.length <= 100;
  //DEBUG	console.log("Il resto contiene " + textAreaValue.length + " caratteri");
  
  const isValidFileName = fileNameValue.trim() != "";
  //DEBUG	console.log("Il nome del file è " + fileNameValue);
  
  // Mostra o nascondi il bottone in base alle condizioni
  submitButton.style.display = (isValidTextArea && isValidFileName) ? "inline-block" : "none";
}
