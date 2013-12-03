package gui;

import java.awt.Dimension;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bll.SettingsPicture;
import bll.ViewController;

public class SettingsContentPanel extends JPanel  {
	/**
	 * @author Andreas J
	 */
	private static final long serialVersionUID = 1L;
	private Dimension dim;
	private HashtagSettingsPanel hashpan;
	private ViewController viewCtrl;
	private JLabel backgroundImageLabel;
	private TableSettingsPanel tablepanel ;
	private  DisplaySettingsPanel dispset;
	public SettingsContentPanel(ViewController viewCtrl,Dimension dim) {
		this.viewCtrl=viewCtrl;
		this.dim=new Dimension(dim.width * 2 / 3,
				dim.height * 7/ 10);
		setPreferredSize(this.dim);
		setDoubleBuffered(true);
		initComponents();
		if (viewCtrl != null) {
			this.hashpan.setHashtagList(viewCtrl.getHashtags());
			this.dispset.setViewDelay(viewCtrl.getDisplayTime());
			this.dispset.setViewMode(viewCtrl.isRandom());
		}
		
		
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
		
		// HashSettingsPanel declaration
		hashpan = new HashtagSettingsPanel();
		hashpan.setBounds( dim.width*1/16, 0, dim.width*25/100,  dim.height*3/4);
		// DisplaySettingsPanel
		dispset = new DisplaySettingsPanel(viewCtrl);
		dispset.setBounds(dim.width*1/16, dim.height*4/5, dim.width*25/100, dim.height*1/10);
		// TableSettingsPanel declaration
		tablepanel = new TableSettingsPanel(viewCtrl,dim);
		tablepanel.setBounds(dim.width*35/100, 10,dim.width*2/3, dim.height*4/3);
		
		
		// Background Image declaration
		
		
		
		
		backgroundImageLabel = new JLabel();

		backgroundImageLabel.setIcon(new ImageIcon(getClass().getResource(
				"/resource/img/backgr.jpg")));
		backgroundImageLabel.setIconTextGap(0);
		//backgroundImageLabel.setPreferredSize(dim);
		///backgroundImageLabel.setBounds(0, 0,dim.width,dim.height);
		backgroundImageLabel.setBounds(0, 0,dim.width,dim.height);
		
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
		//updateTableSettings();
	}
	public void updateTableSettings() {
		ImageTableModel imtabmod = tablepanel.getImageTableModel();
		// not implemented a method to get selected SettingsPicture object and
		// flag them yet.
		// This can be done here for test purposes
		// f.ex we want to flag picture in row 4 column 4:
		imtabmod.setflagOnPicture(4, 4,true);
		imtabmod.setflagOnPicture(3, 4,true);
		// here is the data to send, picture in row 4 colum 4 should be
		// flagged

		List<List<SettingsPicture>> datatosend = imtabmod.getTableModelData();
         viewCtrl.removePictures(datatosend);
         //TODO: send id of pic to DAL } }
		 

	}

	

}