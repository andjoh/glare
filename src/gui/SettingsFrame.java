package gui;
import javax.swing.*;

import bll.ViewController;

import java.awt.event.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;



/**
 * 
 * @author Andreas Johnstad
 */
@SuppressWarnings("serial")
public class SettingsFrame extends JInternalFrame {
private static final Dimension PREFERRED_SIzE= new Dimension(
		800,600
);

private ViewController viewCtrl;
/**
	 * Creates new form SettingsFrame
	 */
	public SettingsFrame(ViewController viewCtrl) {
		this.viewCtrl = viewCtrl;
		initComponents();
		getContentPane().setLayout(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//setResizable(true);
		setPreferredSize(PREFERRED_SIzE);
		setDoubleBuffered(true);
		getContentPane().add(dispset);
		getContentPane().add(tablepanel);
		getContentPane().add(hashpan);
		getContentPane().add(backgroundImageLabel);
		setFrameIcon(new ImageIcon(getClass().getResource("/resource/img/settings.gif")));
		pack();
		setVisible(true);
		
		
	}

	// Legge til en listener ved close window
	// Add metoder for mode osv
	
	
	public void CloseWindowListener(){
		
		// The window is closed
		// Send updates 
	
	/*
	 * 
	 * *	
	 */
		
		Set<String> tmplist =  new HashSet<String>();
		DefaultListModel<String>  tmpmodel = hashpan.getListModel();
		for(int i=0; i<tmpmodel.size();i++){
		tmplist.add(tmpmodel.getElementAt(i));
		}
		// Call method to pass tmplist 
		
		viewCtrl.setHashtags(tmplist);
		
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
				"/resource/img/backgr.jpg")));
		backgroundImageLabel.setIconTextGap(0);
		backgroundImageLabel.setPreferredSize(new Dimension(2560, 1600));
		backgroundImageLabel.setBounds(0, 0, 933, 810);
		
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
//	public static void main(String args[]) {
//		/*
//		 * Set the Nimbus look and feel
//		 */
//		try {
//			for (UIManager.LookAndFeelInfo info : UIManager
//					.getInstalledLookAndFeels()) {
//				if ("Nimbus".equals(info.getName())) {
//					UIManager.setLookAndFeel(info.getClassName());
//					break;
//				}
//			}
//		} catch (ClassNotFoundException ex) {
//			java.util.logging.Logger.getLogger(SettingsFrame.class.getName())
//					.log(java.util.logging.Level.SEVERE, null, ex);
//		} catch (InstantiationException ex) {
//			java.util.logging.Logger.getLogger(SettingsFrame.class.getName())
//					.log(java.util.logging.Level.SEVERE, null, ex);
//		} catch (IllegalAccessException ex) {
//			java.util.logging.Logger.getLogger(SettingsFrame.class.getName())
//					.log(java.util.logging.Level.SEVERE, null, ex);
//		} catch (UnsupportedLookAndFeelException ex) {
//			java.util.logging.Logger.getLogger(SettingsFrame.class.getName())
//					.log(java.util.logging.Level.SEVERE, null, ex);
//		}
//
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				SettingsFrame intfr = new SettingsFrame();
//				JFrame fr = new JFrame();
//				fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				fr.getContentPane().add(intfr);
//				fr.pack();
//				fr.setVisible(true);
//
//			}
//		});
//	}

	// Variables 
	private DisplaySettingsPanel dispset;
    private HashtagSettingsPanel hashpan;
	private TableSettingsPanel tablepanel;
    private JLabel backgroundImageLabel;
}
