package gui;

import glare.ClassFactory;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import bll.*;

public class ShowInterface extends JFrame {
	/**
	 * 
	 * @author Andreas J
	 */
	private ViewController viewCtrl;
	private JButton settingsButton;
	private LoginDialog ld = null;
	private Dimension dim = null;
	private ImageSlider slider;
	JDesktopPane desktopPane;
	private SettingsFrame settingsFrame;
	private JFrame parent = null;
	private ImageShow show;
	// private Constraints gbc = null;
	private GraphicsDevice device;

	// private final ImageShow show;

	public ShowInterface(ViewController viewCtrl) throws IOException {

		this.parent = this;
		this.viewCtrl = viewCtrl;
		settingsFrame = null;
		slider = new ImageSlider();
		//setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setAutoRequestFocus(true);
		//desktopPane= new JDesktopPane();
		
		setContentPane(slider);
		//getContentPane().add(settingsButton);
		//getContentPane().add(slider);
		setLocationByPlatform(true);
		setUndecorated(true);
		pack();
		setVisible(true);
		setFullScreen();
		WindowListener exitListener = new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				System.exit(1);

			}
		};
		addWindowListener(exitListener);
		
		openSettingsFrame();
	}

	

	private void setFullScreen() {
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		device = graphicsEnvironment.getDefaultScreenDevice();
		if (device.isFullScreenSupported()) {
			device.setFullScreenWindow(this);
			this.validate();
		}
	}
	//opens SettingsFrame

	public void openSettingsFrame() {
		System.out.println("Tries to open settingsframe");
		 SettingsFrame settingsFrame = new  SettingsFrame(viewCtrl);

		  slider.add(settingsFrame);
		  settingsFrame.setSize(800, 600);
		  settingsFrame.setLocation(50, 50);
		  settingsFrame.setVisible(true);
		  setVisible(true);

		  // To avoid events from being fired outside the SettingsFrame
		  JPanel gp = new JPanel();
		  gp.setSize(400, 300);
		  gp.setOpaque(true);
		  gp.setVisible(true);
		  gp.setBackground(java.awt.Color.RED);
		  settingsFrame.setGlassPane(gp);

		  // Veto Listener to prevent selection of other components
		

		repaint();

	}
	// opens Dialog Box for login and waits for return value.

	public void openLoginBox() {
		hideComponent(settingsButton);
		boolean loggedIn = new LoginDialog(parent).getSucceeded();
		System.out.println("Login return equals" + loggedIn);
		if (loggedIn)openSettingsFrame();

	}

	private void hideComponent(Component c) {
		c.setVisible(false);
		c.setEnabled(false);
	}

	private void unhideComponent(Component c) {
		c.setVisible(true);
		c.setEnabled(true);
	}

	class ImageSlider extends JComponent implements Runnable, ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1;
		boolean stop = false;
		private Thread th;

		private Action escapeAction;

		public ImageSlider() throws IOException {
			dim = Toolkit.getDefaultToolkit().getScreenSize();
			show = new ImageShow(viewCtrl, (int) dim.getWidth(),
					(int) dim.getHeight());
			escapeAction = new EscapeAction();
			
			
			System.out.println("Widht ImageSlider: "+dim.width+"Height ImageSlider: "+dim.height);
			setPreferredSize(new Dimension(dim.width, dim.height));
			getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"),
					"doEscapeAction");

			getActionMap().put("doEscapeAction", escapeAction);
			setDoubleBuffered(true);
			setFocusable(true);
			setBackground(new Color(223, 219, 218));
			init();
			 
			start();

		}

		private void init() {

			settingsButton = new JButton();
			settingsButton.addActionListener(this);
			settingsButton.setIcon(new ImageIcon(getClass().getResource(
					"/resource/img/settings.gif")));
			settingsButton.setBorderPainted(false);
			settingsButton.setContentAreaFilled(false);
			settingsButton.addActionListener(this);
			int w = 150, h = 150;
			System.out.println(getSize().getHeight());
			settingsButton.setBounds(0,dim.height-h, w, h);
			

		}

		public void start() {
			th = new Thread(this);
			th.start();

		}

		@Override
		public void paintComponents(Graphics g)
	    {
	      super.paintComponent(g);
	        if (show != null) {
				show.paint(g);

			};
	    }
		

		@Override
		public void run() {
			System.out.println("run()");

			try {
				while (stop != true) {

					//System.out.println("before thread");
					Thread.sleep(viewCtrl.getDisplayTime());
					//System.out.println("after thread");
					show.moveNext();
					repaint();
					//System.out.println("ShowINterface, kaller moveNext()");

				}
			} catch (InterruptedException ie) {
				System.out.println("Interrupted slide show...");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}

		public void stopClick() {
			stop = true;

		}

		class EscapeAction extends AbstractAction {
			public void actionPerformed(ActionEvent tf) {
				stopClick();
				System.exit(1);

				System.out.println("Escape");

			}

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(settingsButton)) {
				slider.stopClick();
				openSettingsFrame();
				System.out.println("Check modal");
				//openLoginBox();
			}


	}
	}






}
