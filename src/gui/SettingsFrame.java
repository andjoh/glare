package gui;
import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

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
public class SettingsFrame extends JInternalFrame implements WindowListener, InternalFrameListener, ActionListener {

	private static final Dimension PREFERRED_SIzE= new Dimension(
			800,600
	);

	// Variables 
	private ViewController viewCtrl;
	private DisplaySettingsPanel dispset;
	private HashtagSettingsPanel hashpan;
	private TableSettingsPanel tablepanel;
	private JLabel backgroundImageLabel;
	private JButton saveButton;
	
	/**
	 * Creates new form SettingsFrame
	 */
	public SettingsFrame(ViewController viewCtrl) {
		this.viewCtrl = viewCtrl;
		initComponents();
		

		// Testing add hashtags to list
		hashpan.setHashtagList(viewCtrl.getHashtags());initFrame();
	}
	public SettingsFrame() {
	
		initComponents();
		initFrame();

	}


	private void SendHastagUpdate(){

		// The window is closed
		// Send updates 

		/*
		 * 
		 * *	
		 */

		// Hashtag
		Set<String>  hashtagList = hashpan.getHashtagList();
		viewCtrl.updateHashtags(hashtagList);
	}
	
	/**

	 */
	private void initFrame(){
		getContentPane().setLayout(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//setResizable(true);
		setPreferredSize(PREFERRED_SIzE);
		setDoubleBuffered(true);
		getContentPane().add(dispset);
		getContentPane().add(tablepanel);
		getContentPane().add(hashpan);
		getContentPane().add(saveButton);
		getContentPane().add(backgroundImageLabel);

		setFrameIcon(new ImageIcon(getClass().getResource("/resource/img/settings.gif")));
		pack();
		setVisible(true);
		
	}
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

		// Save settings button declaration
		saveButton = new JButton("Save Settings");
		saveButton.setBounds(660, 530, 105, 28);
		saveButton.addActionListener(this);
		
		// Background Image declaration
		backgroundImageLabel = new JLabel();
		backgroundImageLabel.setIcon(new ImageIcon(getClass().getResource(
		"/resource/img/backgr.jpg")));
		backgroundImageLabel.setIconTextGap(0);
		backgroundImageLabel.setPreferredSize(new Dimension(2560, 1600));
		backgroundImageLabel.setBounds(0, 0, 933, 810);
	}


	@Override
	public void windowActivated(WindowEvent arg0) {
		System.out.println("SettingsFrame: windowActivated");

	}


	@Override
	public void windowClosed(WindowEvent arg0) {
		System.out.println("SettingsFrame: windowClosed");

	}


	@Override
	public void windowClosing(WindowEvent arg0) {
		System.out.println("SettingsFrame: windowClosing");

	}


	@Override
	public void windowDeactivated(WindowEvent arg0) {
		System.out.println("SettingsFrame: windowDeactivated");

	}


	@Override
	public void windowDeiconified(WindowEvent arg0) {
		System.out.println("SettingsFrame: windowDeiconified");

	}


	@Override
	public void windowIconified(WindowEvent arg0) {
		System.out.println("SettingsFrame: windowIconified");

	}


	@Override
	public void windowOpened(WindowEvent arg0) {
		System.out.println("SettingsFrame: windowOpened");

	}


	@Override
	public void internalFrameActivated(InternalFrameEvent arg0) {
		System.out.println("SettingsFrame: internalFrameActivated");		
	}


	@Override
	public void internalFrameClosed(InternalFrameEvent arg0) {
		System.out.println("SettingsFrame: internalFrameClosed");		

	}


	@Override
	public void internalFrameClosing(InternalFrameEvent arg0) {
		System.out.println("SettingsFrame: internalFrameClosing");		

	}


	@Override
	public void internalFrameDeactivated(InternalFrameEvent arg0) {
		System.out.println("SettingsFrame: internalFrameDeactivated");		

	}


	@Override
	public void internalFrameDeiconified(InternalFrameEvent arg0) {
		System.out.println("SettingsFrame: internalFrameDeiconified");		

	}


	@Override
	public void internalFrameIconified(InternalFrameEvent arg0) {
		System.out.println("SettingsFrame: internalFrameIconified");		

	}


	@Override
	public void internalFrameOpened(InternalFrameEvent arg0) {
		System.out.println("SettingsFrame: internalFrameOpened");		

	}


	@Override
	public void actionPerformed(ActionEvent arg0) {

		SendHastagUpdate();
		viewCtrl.setRandom(dispset.getViewMode());
		viewCtrl.setDisplayTime(dispset.getDelay());
	}
}
