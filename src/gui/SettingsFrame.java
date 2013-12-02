package gui;

import bll.ViewController;

import java.awt.event.*;
import java.awt.*;

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
		setModal(true);
		contp = new SettingsContentPanel(viewCtrl, dim);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().add(contp);
		setLocationRelativeTo(parent);
		setUndecorated(true);
		pack();
		setResizable(false);
		setAlwaysOnTop(true);
		requestFocusInWindow();

		JButton jb = new JButton("Save");

		contp.add(jb);
		setLocationRelativeTo(parent);
		// parent.pack();

		// Testing add hashtags to list
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				contp.saveBeforeExit();

			}
			/*
			 * @Override public void windowClosed(WindowEvent e) {
			 * 
			 * System.out.println("SettingsFrame is closed"); //
			 * setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); }
			 */
		});
		setVisible(true);
	}

	public boolean validationExit() {
		return true;
	}

}
