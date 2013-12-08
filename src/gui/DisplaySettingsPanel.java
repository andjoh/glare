package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import bll.ViewController;
/**
 * @author Andreas J
 */
public class DisplaySettingsPanel extends JPanel {
	/**
	*   Panel that shows GUI for Setting Displays
	*  
	*/
	private JSpinner delaySpinner; // Spinner box, user can click through a defined range of values
	private JComboBox<String> viewmodeCombo; // combobox. User can click to select predefined options
	private JLabel viewdelayLabel, viewmodeLabel;  // label that shows the text "View Delay"
	private static final long serialVersionUID = 1L;
	private static int SPINNER_DEFAULT = 3; // the default value of the spinner
	private final int SPINNER_MINIMUM = 1, SPINNER_MAXIMUM = 9; // constant min,max values for spinner
	private Constraints[] gbcs = new Constraints[] { new Constraints(), // constraints for GBC layout
			new Constraints(), new Constraints(), new Constraints() };

	/**
	 * @param viewCtrl
	 */
	public DisplaySettingsPanel(ViewController viewCtrl) {
		setOpaque(false);
		setLayout(new GridBagLayout());
		init();
		setConstraints();
	}
	/**
	*  Method called by the constructor to 
	*  Initialize GUI components
	*/
	private void init() {
		// delaySpinner properties
		delaySpinner = new JSpinner();
		delaySpinner.setEditor(new JSpinner.DefaultEditor(delaySpinner));
		delaySpinner.setModel(new SpinnerNumberModel(SPINNER_DEFAULT,
				SPINNER_MINIMUM, SPINNER_MAXIMUM, 1));
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
	/**
	*  Method called by the constructor to 
	*  Set the Constraints for GUI components 
	*  And add them to the Panel
	*  
	*/
  
	private void setConstraints() {
		int gc = GridBagConstraints.NORTHWEST;
		// set properties jspinner

		gbcs[0].set(4, 0, 1, 1, 0, 0,1.0,1.0, new Insets(0, 0, 0, 0), gc);
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
	/**
	*  Method that returns the state of the combobox
	*  <p>
	*  Returned value of true indicates that 
	*  The Selected viewmode is should be set to
	*  Random
	*  <p>
	*  @return boolean b
	*  
	*/

	public boolean getViewMode() {
		Object obj = viewmodeCombo.getSelectedItem();
		boolean b = false;
		if (obj instanceof String) {
			if (obj.equals("randomly")) {
				b = true;
			}
		}
		return b;

	}
	/**
	*  Method that sets the state of the combobox
	*  <p>
	*  True sets the selected item to "randomly"
	*  False sets the selected item to "sequential"
	*  Random
	*  <p>
	*  @param boolean b
	*  
	*/
	public void setViewMode(boolean isRandom) {
		if (isRandom)
			viewmodeCombo.setSelectedItem("randomly");
		else
			viewmodeCombo.setSelectedItem("sequential");
	}
	/**
	 * Gets the current selected value for the JSpinner
	 * @return int;
	 */

	public int getViewDelay() {
		int i = (Integer) delaySpinner.getValue();
		return i;

	}
	/**
	 * Sets the current selected value for the JSpinner
	 * <p>Sets default value, performs 
	 * Check to see if the parameter is within 
	 * The specified range
	 * <p>
	 * @param delay;
	 */

	public void setViewDelay(int delay) {
		if (delay >= SPINNER_MINIMUM && delay <= SPINNER_MAXIMUM) {
			delaySpinner.setValue(new Integer(delay));
		}
	}

}