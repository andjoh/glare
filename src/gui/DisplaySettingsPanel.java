package gui;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class DisplaySettingsPanel extends JPanel implements ActionListener {
	/**
		 * 
		 */
	private JSpinner delaySpinner;
	private JComboBox<String> viewmodeCombo;
	private JLabel viewdelayLabel, viewmodeLabel;
	private static final long serialVersionUID = 1L;
	private Constraints[] gbcs = new Constraints[] { new Constraints(),
			new Constraints(), new Constraints(), new Constraints() };

	public DisplaySettingsPanel() {

		setMaximumSize(new Dimension(10800, 9720));
		setOpaque(false);
		setLayout(new GridBagLayout());

		init();
		setConstraints();
	}

	private void init() {
		// delaySpinner properties
		delaySpinner = new JSpinner();
		delaySpinner.setEditor(new JSpinner.DefaultEditor(delaySpinner));
		delaySpinner.setModel(new SpinnerNumberModel(3, 1, 10, 1));
		delaySpinner.setOpaque(false);
		delaySpinner.setBorder(null);

		// viewmodeCombo properties
		viewmodeCombo = new JComboBox<String>();
		viewmodeCombo.setModel(new DefaultComboBoxModel<String>(new String[] {
				"sequential", "randomly" }));
		viewmodeCombo.setBorder(BorderFactory.createEtchedBorder());
		viewmodeCombo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		viewmodeCombo.setOpaque(false);
		viewmodeCombo.setBorder(null);
		// viewdelayLabel properties
		viewdelayLabel = new JLabel("Delay");
		viewdelayLabel.setFont(new Font("Tahoma", 0, 14));
		viewdelayLabel.setForeground(new Color(255, 255, 255));
		// viewmodeLabel
		viewmodeLabel = new JLabel("View mode");
		viewmodeLabel.setFont(new Font("Tahoma", 0, 14));
		viewmodeLabel.setForeground(new Color(255, 255, 255));

	}

	private void setConstraints() {
		int gc = GridBagConstraints.NORTHWEST;
		// set properties jspinner

		gbcs[0].set(4, 0, 1, 1, 0, 0, new Insets(0, 0, 0, 0), gc);
		add(delaySpinner, gbcs[0]);
		// delayspinner combobox

		gbcs[1].set(4, 1, 2, 1, 0, 0, new Insets(0, 0, 0, 0), gc);
		add(viewmodeCombo, gbcs[1]);
		// setproperties viewdelayLabel

		gbcs[2].set(0, 0, 1, 1, 0, 0, new Insets(0, 0, 0, 0), gc);
		add(viewdelayLabel, gbcs[2]);
		// setproperties viewmodeLabel

		gbcs[3].set(0, 1, 2, 1, 0, 0, new Insets(0, 0, 0, 0), gc);
		add(viewmodeLabel, gbcs[3]);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public boolean getViewMode() {
		if ( viewmodeCombo.getSelectedItem().equals("randomly") ) {
			return true;
		}
		
		return false;
	}

	public int getDelay() {
		int delay = (Integer) delaySpinner.getValue();
		
		return delay;
	}
	
}