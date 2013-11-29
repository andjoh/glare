package gui;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.MouseInputAdapter;

import bll.SettingsPicture;
import bll.ViewController;

import java.awt.event.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Andreas Johnstad
 */
@SuppressWarnings("serial")
final public class SettingsFrame extends JDialog {

	private static final Dimension PREFERRED_SIZE = new Dimension(800, 600);

	// Variables
	private ViewController viewCtrl;
	private SettingsContentPanel contp;
	private TableSettingsPanel tablepanel;
	private JFrame parent;
	private Dimension dim;

	public SettingsFrame(ViewController viewCtrl) {
		this.parent = parent;
		this.viewCtrl = viewCtrl;
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		//setSize(dim.width * 2 / 3, dim.width * 2 / 4);
		setModal(true);
		contp = new SettingsContentPanel(viewCtrl, dim);  
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().add(contp);
		setLocationRelativeTo(parent);
		pack();
		setResizable(false);
		setAlwaysOnTop(true);
		requestFocusInWindow();
		
		JButton jb = new JButton("Save");
		
		contp.add(jb);
		setLocationRelativeTo(parent);
		//parent.pack();
		
		// Testing add hashtags to list
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
				System.out.println("SettingsFrame is closing");
				
				
				
			}
			/*
			@Override
			public void windowClosed(WindowEvent e) {
		
				System.out.println("SettingsFrame is closed");
			//	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}*/
		});
		setVisible(true);
	}



	public boolean validationExit() {
		return true;
	}

	
}
