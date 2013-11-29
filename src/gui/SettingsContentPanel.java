package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bll.SettingsPicture;
import bll.ViewController;

public class SettingsContentPanel extends JPanel  {
	private Dimension dim;
	private HashtagSettingsPanel hashpan;
	private ViewController viewCtrl;
	private JLabel backgroundImageLabel;
	private TableSettingsPanel tablepanel ;
	private  DisplaySettingsPanel dispset;
	public SettingsContentPanel(ViewController viewCtrl,Dimension dim) {
		this.viewCtrl=viewCtrl;
		this.dim=new Dimension(dim.width * 2 / 3,
				dim.height * 2 / 3);
		//setPreferredSize(dim);
		setPreferredSize(new Dimension(800,600));
		setDoubleBuffered(true);
		initComponents();
		if (viewCtrl != null)this.hashpan.setHashtagList(viewCtrl.getHashtags());
		addComponents();
		
	}

	public void addComponents() {
		setLayout(null);
		add(dispset);
		add(tablepanel);
		add(hashpan);
		add(backgroundImageLabel);
	}

	private void initComponents() {
		// TableSettingsPanel declaration
		tablepanel = new TableSettingsPanel(viewCtrl,dim);
		tablepanel.setBounds(300, 10, 510, 510);
		// HashSettingsPanel declaration
		hashpan = new HashtagSettingsPanel(viewCtrl,dim);
		hashpan.setBounds(50, -12, 180, 320);
		// DisplaySettingsPanel
		dispset = new DisplaySettingsPanel(viewCtrl,dim);
		dispset.setBounds(60, 360, 185, 70);
		// Background Image declaration
		backgroundImageLabel = new JLabel();

		backgroundImageLabel.setIcon(new ImageIcon(getClass().getResource(
				"/resource/img/backgr.jpg")));
		backgroundImageLabel.setIconTextGap(0);
		//backgroundImageLabel.setPreferredSize(dim);
		///backgroundImageLabel.setBounds(0, 0,dim.width,dim.height);
		backgroundImageLabel.setBounds(0, 0,800,600);
		
	}
	public void updateDisplaySettings() {
		if(dispset==null)System.out.println("Dispset is null");
		if(dispset==null)System.out.println("Dispset is null");
		viewCtrl.setRandom(dispset.getViewMode());
		viewCtrl.setDisplayTime(dispset.getViewDelay());
	}

	// send updated hashtags to ViewCtrl
	public void updateHashtags() {
		if(hashpan==null)System.out.println("hashpan is null");
		Set<String> hashtagList = hashpan.getHashtagList();
	
		viewCtrl.updateHashtags(hashtagList);
	}
	public void saveBeforeExit(){
		updateDisplaySettings();
		updateHashtags();
		updateTableSettings();
	}
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
         viewCtrl.removePictures(datatosend);
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

	

}