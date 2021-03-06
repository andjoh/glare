package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.coobird.thumbnailator.Thumbnails;

import bll.SettingsPicture;
import bll.ViewController;

/**
 * @author Andreas Johnstad
 * 
 * @param <exitButton>
 */
/**
 * @author Andreas Johnstad
 *
 * @param <exitButton>
 */
public class SettingsContentPanel extends JPanel implements ActionListener {
	/**
	 * @author Andreas J
	 */
	private static final long serialVersionUID = 1L;
	private Dimension dim; // dimensions of this panel
	private JButton exitButton; // button to exit this window
	private HashtagSettingsPanel hashpan; // reference to hashtag settings panel
	private ViewController viewCtrl;  // the view controller
	private SettingsFrame parent; 
	private JLabel backgroundImageLabel;    // label containing back ground image
	private TableSettingsPanel tablepanel;  // reference to table settings panel
	private DisplaySettingsPanel dispset;  // reference to display settings panel

	/**
	 * @param viewCtrl
	 * @param parent
	 * @param dim
	 */
	public SettingsContentPanel(ViewController viewCtrl, SettingsFrame parent, Dimension dim) {
		this.viewCtrl = viewCtrl;
		this.parent = parent;
		this.dim = new Dimension(dim.width * 2 / 3, dim.height * 7 / 10);
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
		add(exitButton);
		add(backgroundImageLabel);
	}

	private void initComponents() {

		// HashSettingsPanel declaration
		hashpan = new HashtagSettingsPanel();
		hashpan.setBounds(dim.width * 1 / 16, 0, dim.width * 25 / 100, dim.height * 3 / 4);
		// DisplaySettingsPanel
		dispset = new DisplaySettingsPanel(viewCtrl);
		dispset.setBounds(dim.width * 1 / 20, dim.height * 4 / 5, dim.width * 25 / 100, dim.height * 1 / 10);
		// TableSettingsPanel declaration
		tablepanel = new TableSettingsPanel(viewCtrl, dim);
		tablepanel.setBounds(dim.width * 30 / 100, 10, dim.width * 2 / 3, dim.height * 4 / 3);
		// exitButton properties

		exitButton = new JButton("Exit");

		exitButton.addActionListener(this);

		BufferedImage img = null;
		URL url = this.getClass().getResource("/resource/img/exit.png");
		int w = dim.width / 20, h = dim.height / 20;
		try {
			img = ImageIO.read(url);
			img = Thumbnails.of(img).size(w, h).asBufferedImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageIcon ic = new ImageIcon(img);

		exitButton.setIcon(ic);
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setBounds(dim.width - w * 2 / 3, 0, w, h);

		// Background Image declaration

		backgroundImageLabel = new JLabel();
		backgroundImageLabel.setIcon(new ImageIcon(getClass().getResource("/resource/img/backgr.jpg")));
		backgroundImageLabel.setIconTextGap(0);
		backgroundImageLabel.setBounds(0, 0, dim.width, dim.height);

	}

	public void updateDisplaySettings() {
		viewCtrl.setRandom(dispset.getViewMode());
		viewCtrl.setDisplayTime(dispset.getViewDelay());
	}

	/**
	 * // send updated hashtags to ViewCtrl
	 */
	public void updateHashtags() {
		Set<String> hashtagList = hashpan.getHashtagList();

		viewCtrl.updateHashtags(hashtagList);
	}

	public void saveBeforeExit() {
		updateDisplaySettings();
		updateHashtags();
		updateTableSettings();
	}

	public void updateTableSettings() {
		ImageTableModel imtabmod = tablepanel.getImageTableModel();
		//data to send to view controller
		List<SettingsPicture> datatosend = imtabmod.getTableModelData();
		// sends data to viewcontroller who checks for flagged
		// SettingsPictures 
		viewCtrl.removePictures(datatosend);

	}

	/**
	 *  (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(exitButton)) {
			saveBeforeExit();
			parent.setVisible(false);
			parent.dispatchEvent(new WindowEvent(parent,
					WindowEvent.WINDOW_CLOSING));
		}

	}

}