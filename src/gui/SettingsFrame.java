package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JFrame;
import bll.ViewController;

/**
 * 
 * @author Andreas Johnstad
 */

@SuppressWarnings("serial")
final public class SettingsFrame extends JDialog {
	/**
	 * Modal JDialog box that contains GUI for settings
	 */
	private SettingsContentPanel contp; // Panel that contains all settings GUI

	private JFrame parent; // the parent : showinterface
	private Dimension dim; // dimensions of this Dialog

	/**
	 * @param viewCtrl
	 */
	public SettingsFrame(ViewController viewCtrl) {
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		contp = new SettingsContentPanel(viewCtrl,this, dim);
		getContentPane().add(contp);
		setLocationRelativeTo(parent);
		setUndecorated(true);
		pack();
		setResizable(false);
		setAlwaysOnTop(true);
		requestFocusInWindow();
		setLocationRelativeTo(parent);
		setModal(true);
		setVisible(true);
	}
}
