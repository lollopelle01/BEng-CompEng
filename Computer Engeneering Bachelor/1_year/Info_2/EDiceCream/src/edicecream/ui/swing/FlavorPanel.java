package edicecream.ui.swing;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FlavorPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private HashMap<String, JCheckBox> boxes;
	private int maxSelected;
	private int selected;

	public FlavorPanel(Set<String> flavors) {
		this.boxes = new HashMap<>();
		this.maxSelected = 0;
		this.selected = 0;
		initPanel(flavors);
	}

	private void initPanel(Set<String> flavors) {
		for (String fl : flavors) {
			JCheckBox cb = new JCheckBox(fl);
			cb.addActionListener(event -> {
				AbstractButton abstractButton = (AbstractButton) event.getSource();
		        boolean value = abstractButton.getModel().isSelected();
				if (value) {
					if (selected + 1 > maxSelected) {
						cb.setSelected(false);
						JOptionPane.showMessageDialog(null, "Massimo numero di gusti raggiunto.");
					} else {
						selected++;
					}
				} else {
					selected--;
				}
			});
			this.boxes.put(fl, cb);
			add(cb);
		}
		setBackground(Color.WHITE);
	}

	public void setMaxSelected(int maxSelected) {
		this.maxSelected = maxSelected;
		for (String fl : boxes.keySet()) {
			JCheckBox cb = boxes.get(fl);
			cb.setSelected(false);
		}
		this.selected = 0;
	}

	public List<String> getSelected() {
		ArrayList<String> selected = new ArrayList<>();
		for (String fl : boxes.keySet()) {
			JCheckBox cb = boxes.get(fl);
			if (cb.isSelected()) {
				selected.add(fl);
			}

		}
		return selected;
	}

	public void reset() {
		for (String fl : boxes.keySet()) {
			JCheckBox cb = boxes.get(fl);
			cb.setSelected(false);
		}
		selected = 0;
	}

}
