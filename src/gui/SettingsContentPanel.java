package gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bll.SettingsPicture;
import bll.ViewController;

/**
 * @author Andreas Johnstad
 * 
 */


public class SettingsContentPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private ViewController viewCtrl; // the viewController
	private SettingsFrame parent; // parent : ShowInterface (JFrame)
    private Dimension dim; // dimensions for this Panel
    private Rectangle hashBounds,tableBounds,dispBounds;
    private HashtagSettingsPanel hashpan; // subpanel : hash tags settings
	private TableSettingsPanel tablepanel; // subpanel : table/thumbnail settings
    private DisplaySettingsPanel dispset; // subpanel display settings
    private JLabel backgroundImageLabel; // label to contain background image
	private JButton exitButton; // button to close window
	private LoadLocalImage loader; //  class with method to load a local image

	/**
	 * @param viewCtrl
	 * @param parent
	 * @param dim
	 */
	public SettingsContentPanel(ViewController viewCtrl, SettingsFrame parent,
			Dimension dim) {
		this.viewCtrl = viewCtrl;
		this.parent = parent;
		setOpaque(false);
		this.dim = new Dimension(dim.width * 2 / 3, dim.height * 7 / 10);
		setPreferredSize(this.dim);
		setDoubleBuffered(true);
		init();

		if (viewCtrl != null) {
			this.hashpan.setHashtagList(viewCtrl.getHashtags());
			this.dispset.setViewDelay(viewCtrl.getDisplayTime());
			this.dispset.setViewMode(viewCtrl.isRandom());
		}
		addComponents();
	}

	private void addComponents() {
		setLayout(null);
		setBounds();
		// set bounds of the hashtag settings panel, add
		hashpan.setBounds(hashBounds);
		add(hashpan);
		
		// set bounds of the table settings panel, add
		tablepanel.setBounds(tableBounds);
		add(tablepanel);
		
		// set bounds of the display settings panel, add
		dispset.setBounds(dispBounds);
		add(dispset);
		
		// set bounds, add
		add(exitButton);
		add(backgroundImageLabel);
	}

	private void init() {
        loader= new LoadLocalImage();
		
		// HashSettingsPanel declaration
		hashpan = new HashtagSettingsPanel();
		
		// DisplaySettingsPanel declaration
		dispset = new DisplaySettingsPanel(viewCtrl);
	
		// TableSettingsPanel declaration
		tablepanel = new TableSettingsPanel(viewCtrl, dim);
		
		// exitButton properties

		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);
		int w = dim.width / 20, h = dim.height / 20;
		exitButton.setIcon(loader.getIcon(dim.width / 20,dim.height / 20,"/resource/img/exit.png"));
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setBounds(dim.width - w * 2 / 3, 0, w, h);

		// Background Image declaration

		backgroundImageLabel = new JLabel();
		backgroundImageLabel.setIcon(new ImageIcon(getClass().getResource(
		"/resource/img/backgr.jpg")));
		backgroundImageLabel.setIconTextGap(0);
		backgroundImageLabel.setBounds(0, 0, dim.width, dim.height);
	}
	private void setBounds(){
		// set Bounds 
		hashBounds = new Rectangle(dim.width * 1 / 30, 0, dim.width * 25 / 100,
				dim.height * 3 / 4);
		dispBounds = new Rectangle(dim.width * 1 / 20, dim.height * 4 / 5,
				dim.width * 30 / 100, dim.height * 1 / 8);
		tableBounds =new Rectangle(dim.width * 30 / 100, dim.height/100, dim.width*2/3,
				dim.height * 4 / 3);	
	}


	/**
	 * Passes the current state of the viewmode and view delay
	 * To viewController
	 * Called when dialog is closed
	 */
	public void updateDisplaySettings() {

		viewCtrl.setRandom(dispset.getViewMode());
		viewCtrl.setDisplayTime(dispset.getViewDelay());
	}

	/**
	 * Passes the current state of the hashtag list (JList)
	 * To viewController
	 * Called when dialog is closed
	 */
	public void updateHashtags() {
		// the data to be sent
		Set<String> hashtagList = hashpan.getHashtagList();
        // the method to send them
		viewCtrl.updateHashtags(hashtagList);
	}
	/**
	 * Passes the current state of the table
	 * To viewController: Sends a list of the contained SettingsPicture 
	 * Objects. Some of which may be flagged.
	 * Called when dialog is closed
	 */
	public void updateTableSettings() {
		ImageTableModel imtabmod = tablepanel.getImageTableModel();
	   //  The data to be sent
		List<SettingsPicture> datatosend = imtabmod.getTableModelData();
		// The method to send them
		 viewCtrl.removePictures(datatosend);
	}
	
	
	/**
	 * Calls methods to pass the current states 
	 * To the ViewController before exiting
	 */
	public void saveBeforeExit() {
		updateDisplaySettings();
		updateHashtags();
		updateTableSettings();
	}

	
    /**
	 * 
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// If exit button is clicked 
		// Save and dispatch 
		if (e.getSource().equals(exitButton)) {
			saveBeforeExit();
			parent.setVisible(false);
			parent.dispatchEvent(new WindowEvent(parent,
					WindowEvent.WINDOW_CLOSING));
		}

	}

}