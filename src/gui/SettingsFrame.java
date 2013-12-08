package gui;

import bll.ViewController;

import java.awt.*;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * 
 * @author Andreas Johnstad
 */
@SuppressWarnings("serial")
final public class SettingsFrame extends JDialog {

	private SettingsContentPanel contp;
	private JFrame parent;
	private Dimension dim;

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

	public boolean validationExit() {
		return true;
	}

}
