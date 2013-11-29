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
	private DisplaySettingsPanel dispset;
	private HashtagSettingsPanel hashpan;
	private TableSettingsPanel tablepanel;
	private JFrame parent;
	private Dimension dim;

	public SettingsFrame(ViewController viewCtrl, JFrame parent) {
		this.parent = parent;
		this.viewCtrl = viewCtrl;
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		//setSize(dim.width * 2 / 3, dim.width * 2 / 4);
		setModal(true);
		contp = new SettingsContentPanel(viewCtrl, dim, hashpan, dispset, tablepanel);  
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().add(contp);
		setLocationRelativeTo(parent);
		pack();
		setResizable(false);
		setAlwaysOnTop(true);
		requestFocusInWindow();
		setLocationRelativeTo(parent);
		//parent.pack();
		setVisible(true);
		// Testing add hashtags to list
	}

	// Will be called when the frame is in the process of closing

	// Will call the necessary methods to update settings

	public void updateTableSettings() {
		ImageTableModel imtabmod = tablepanel.getImageTableModel();
		// not implemented a method to get selected SettingsPicture object and
		// flag them yet.
		// This can be done here for test purposes
		// f.ex we want to flag picture in row 4 column 4:
		imtabmod.flagPicture(4, 4);
		imtabmod.flagPicture(3, 4);
		// here is the data to send, picture in row 4 colum 4 should be
		// flagged

		List<List<SettingsPicture>> datatosend = imtabmod.getTableModelData();

		// TODO: iterate through the data
		// can be done with enchanced for loop, like this:
		/*
		 * for(List<SettingsPicture> row: datatosend){
		 * 
		 * for(SettingsPicture pic: row){
		 * 
		 * //TODO: send id of pic to DAL } }
		 */

	}

	public boolean validationExit() {
		return true;
	}

	// / send updated display settings

	// get updated displaysettings and set them
	public void updateDisplaySettings() {
		viewCtrl.setRandom(dispset.getViewMode());
		viewCtrl.setDisplayTime(dispset.getViewDelay());
	}

	// send updated hashtags to ViewCtrl
	public void updateHashtags() {
		Set<String> hashtagList = hashpan.getHashtagList();
		viewCtrl.updateHashtags(hashtagList);
	}
}
