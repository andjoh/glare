package gui;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bll.ViewController;

public class SettingsContentPanel extends JPanel {
	private Dimension dim;
	private HashtagSettingsPanel hashpan;
	private ViewController viewCtrl=null;
	private JLabel backgroundImageLabel;
	private TableSettingsPanel tablepanel ;
	private  DisplaySettingsPanel dispset;
	public SettingsContentPanel(ViewController viewCtrl,Dimension dim, HashtagSettingsPanel hashpan, DisplaySettingsPanel dispset, TableSettingsPanel tablepanel) {
		this.viewCtrl=viewCtrl;
		this.dim=new Dimension(dim.width * 2 / 3,
				dim.height * 2 / 3);
		this.hashpan=hashpan;
		this.dispset=dispset;
		this.tablepanel=tablepanel;
		//setPreferredSize(dim);
		setPreferredSize(new Dimension(800,600));
		setDoubleBuffered(true);
		initComponents();
		addComponents();
		if (viewCtrl != null)
			hashpan.setHashtagList(viewCtrl.getHashtags());
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

}