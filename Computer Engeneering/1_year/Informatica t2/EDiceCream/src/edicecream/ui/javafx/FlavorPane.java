package edicecream.ui.javafx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.FlowPane;

public class FlavorPane extends FlowPane {
	private HashMap<String, CheckBox> boxes;
	private int maxSelected;
	private int selected;

	public FlavorPane(Set<String> flavors) {
		this.boxes = new HashMap<>();
		this.maxSelected = 0;
		this.selected = 0;
		initPane(flavors);
	}

	private void initPane(Set<String> flavors) {
		this.setVgap(10);
		this.setHgap(10);
		for (String fl : flavors) {
			CheckBox cb = new CheckBox(fl);
			cb.setStyle("-fx-font-size: 20;" + "-fx-border-insets: -5; " + "-fx-border-width: 2;");
			cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
					if (new_val != null && new_val.booleanValue()) {
						selected++;
						if (selected > maxSelected) {
							cb.setSelected(false);
							alert("Attenzione", "Numero gusti", "Massimo numero gusti raggiunto.");
						}
					} else {
						selected--;
					}
				}
			});
			this.boxes.put(fl, cb);
			getChildren().add(cb);
		}
	}

	public void setMaxSelected(int maxSelected) {
		this.maxSelected = maxSelected;
		for (String fl : boxes.keySet()) {
			CheckBox cb = boxes.get(fl);
			cb.setSelected(false);
		}
		this.selected = 0;
	}

	public List<String> getSelected() {
		ArrayList<String> selected = new ArrayList<>();
		for (String fl : boxes.keySet()) {
			CheckBox cb = boxes.get(fl);
			if (cb.isSelected()) {
				selected.add(fl);
			}

		}
		return selected;
	}

	public void reset() {
		for (String fl : boxes.keySet()) {
			CheckBox cb = boxes.get(fl);
			cb.setSelected(false);
		}
		selected = 0;
	}

	private void alert(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}

}
