package gui;
import javax.swing.*;

import java.awt.event.*;
import java.awt.*;



/**
 * 
 * @author Andreas Johnstad
 */
@SuppressWarnings("serial")
public class SettingsFrame extends JInternalFrame {

	private static final String UNCHECKED = "unchecked";
	private static final String UNCHECKED2 = UNCHECKED;

	/**
	 * Creates new form SettingsFrame
	 */
	public SettingsFrame() {
		initComponents();
		getContentPane().setLayout(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//setResizable(true);
		setPreferredSize(new Dimension(800,600));
		setDoubleBuffered(true);
		getContentPane().add(dispset);
		getContentPane().add(tablepanel);
		getContentPane().add(hashpan);
		getContentPane().add(backgroundImageLabel);
		setFrameIcon(new ImageIcon(getClass().getResource("settings.gif")));
		pack();
		setVisible(true);
		
		
	}

	/**
	 
	 */
	private void initComponents() {
        // TableSettingsPanel declaration
		tablepanel = new TableSettingsPanel();
		tablepanel.setBounds(300, 10, 510, 510);
		// HashSettingsPanel declaration
		hashpan = new HashtagSettingsPanel();
		hashpan.setBounds(50, -12, 180, 320);
	    // DisplaySettingsPanel
		dispset = new DisplaySettingsPanel();
		dispset.setBounds(60, 360, 185, 70);
		// Background Image declaration
		backgroundImageLabel = new JLabel();
	
		backgroundImageLabel.setIcon(new ImageIcon(getClass().getResource(
				"backgr.jpg")));
		backgroundImageLabel.setIconTextGap(0);
		backgroundImageLabel.setMaximumSize(new Dimension(8192, 4608));
		backgroundImageLabel.setMinimumSize(new Dimension(800, 600));
		backgroundImageLabel.setPreferredSize(new Dimension(2560, 1600));
		backgroundImageLabel.setBounds(0, 0, 933, 810);
		
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/*
		 * Set the Nimbus look and feel
		 */
		try {
			for (UIManager.LookAndFeelInfo info : UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(SettingsFrame.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(SettingsFrame.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(SettingsFrame.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(SettingsFrame.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				SettingsFrame intfr = new SettingsFrame();
				JFrame fr = new JFrame();
				fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				fr.getContentPane().add(intfr);
				fr.pack();
				fr.setVisible(true);

			}
		});
	}

	// Variables 
	private DisplaySettingsPanel dispset;
	private DefaultListModel<String> listModel;
	private JTextField addhashField;
	private JSpinner delaySpinner;

	private HashtagSettingsPanel hashpan;
	private JList<String> hashJList;
	private JLabel hashsignLabel;
	private JButton removeButton, delhashButton, addhashButton;
	@SuppressWarnings("rawtypes")
	private JComboBox viewmodeCombo;
	private JScrollPane jlistScroller;
	private JScrollPane tableScroller;
	private TableSettingsPanel tablepanel;
	private ImageTable thumbnailTable;
	private JLabel viewdelayLabel, viewmodeLabel, backgroundImageLabel;
}
