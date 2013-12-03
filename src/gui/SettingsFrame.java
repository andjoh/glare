package gui;

import bll.ViewController;

import java.awt.event.*;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
		// setSize(dim.width * 2 / 3, dim.width * 2 / 4);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		contp = new SettingsContentPanel(viewCtrl, dim);
		
		getContentPane().add(contp);
		setLocationRelativeTo(parent);
		//setUndecorated(true);
		pack();
		setResizable(false);
		setAlwaysOnTop(true);
		requestFocusInWindow();
		setLocationRelativeTo(parent);
		// Testing add hashtags to list
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				contp.saveBeforeExit();

			}
		});
		//Image img = new ImageIcon(SettingsFrame.class.getResource("settings.gif")).getImage();
		//((java.awt.Frame)this.getOwner()).setIconImage(img);
		setModal(true);
		
		setVisible(true);
	}

	public boolean validationExit() {
		return true;
	}

}
