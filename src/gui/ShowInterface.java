package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

import bll.*;

/**
 * @author Andreas Johnstad
 *
 */

public class ShowInterface extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dimension dim = null; // Dimensions for this Fram
	private LoginDialog ld = null; // LoginDialog: dialog box for login
	private ShowInterface parent = null; // reference to this class
	private JButton settingsButton; // Button to open login
	@SuppressWarnings("unused")
	private SettingsFrame settingsFrame; // SettingsFrame
	private ImageSlider slider; // Subclass to shoe imges i
	private ViewController viewCtrl; // The View Controller

	public ShowInterface(ViewController viewCtrl) throws IOException {
		this.dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.parent = this;
		this.viewCtrl = viewCtrl;
		settingsFrame = null;

		slider = new ImageSlider(viewCtrl, dim);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setAutoRequestFocus(true);
		requestFocusInWindow();
		init();
		
		//getContentPane().add(settingsButton);
		//getContentPane().add(slider);
		setContentPane(slider);   
		add(settingsButton);   
		// Add windowlistener with overriden methods
		// To get wanted behavior on closing, and icconifying

		setUndecorated(true);
		pack();
		setVisible(true);
		setFullScreen();
		setResizable(false);
		
		addListeners();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(settingsButton)
				&& (ld == null || !ld.isVisible())) {
			openLoginBox();

		}
	}
	/**
	 * 
	 */
	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {

				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {

			}

			@Override
			public void windowIconified(WindowEvent arg0) {

			}
		});

		addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowLostFocus(WindowEvent e) {
			
			}
		});
		
		// listener for key inputs : listens for escape
		@SuppressWarnings("serial")
		Action actionListener = new AbstractAction() {
			// user pressed ecape : stop slideshow,call closeWindow.
			public void actionPerformed(ActionEvent actionEvent) {
				slider.stopClick();
				closeWindow();
			}
		};
        // casts getContentPane() to a jpanel
		// gets input map
		JPanel pane = (JPanel) this.getContentPane();
		KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");

		InputMap inputMap = pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(stroke, "ESCAPE");
		pane.getActionMap().put("ESCAPE", actionListener);

	}

	private void init(){
			settingsButton = new JButton();
			settingsButton.addActionListener(this);
			settingsButton.setIcon(new ImageIcon(getClass().getResource(
					"/resource/img/settingsbut.png")));
		//	settingsButton.setRolloverIcon(new ImageIcon(getClass().getResource(
		//	"/resource/img/settingshover.png")));
			settingsButton.setPressedIcon(new ImageIcon(getClass().getResource(
					"/resource/img/settingsbut.png")));
		settingsButton.setBorderPainted(false);
			settingsButton.setContentAreaFilled(false);
		    int w=dim.width/10,  h=dim.height/15,x=0,y=dim.height-h;
		    getContentPane().setLayout(null);
		    settingsButton.setBounds(x,y,w,h);
		   
		


		
	}
	/**
	 * Dispatch and Close 
	 */
	private void closeWindow() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

	/**
	 * Stop slideshow,hide settings button open login window,h
	 * <p>
	 * Open settings dialog if correct info entered If not. Show button, start
	 * slideshow
	 * <p>
	 */
	private void openLoginBox() {
		slider.stopClick();
		showButton(true);
		ld = new LoginDialog(parent);
		boolean suc = ld.getSucceeded();
		if (suc)
			openSettingsFrame();
		else {
			slider.start();
			showButton(true);
		}
	}

	/**
	 * Open setting window, hide settingsbutton Stop slideshow
	 */
	private void openSettingsFrame() {
		settingsFrame = new SettingsFrame(viewCtrl);
		showButton(true);
		slider.start();
	}

	/**
	 * // Set the full screen
	 */
	private void setFullScreen() {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	/**
	 * Hide or unhide settings button
	 * 
	 * @param shouldHide
	 */
	private void showButton(boolean shouldHide) {
		settingsButton.setVisible(shouldHide);
		settingsButton.setEnabled(shouldHide);
	}

}
