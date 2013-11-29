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
	private WindowAdapter windowAdapter = null;
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
		setLayout(null);
		setDefaultCloseOperation(ShowInterface.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		setSize(800, 600);
		setAutoRequestFocus(true);
		requestFocusInWindow();
		setContentPane(slider);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			}
		});

		setUndecorated(true);
		pack();
		setVisible(true);
		setFullScreen();

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

	public void openSettingsFrame() {
		SettingsFrame dialog = new SettingsFrame(viewCtrl, this);

		boolean suc = dialog.validationExit();
		if (suc)
			unhideComponent(settingsButton);
		System.out.println("Returns from settingsFrame" + suc);

	}

	public void openLoginBox() {
		hideComponent(settingsButton);
		ld = new LoginDialog(parent);
		boolean suc = ld.getSucceeded();

		System.out.println("Login return equals" + suc);
		if (suc) {
			openSettingsFrame();
		} else
			unhideComponent(settingsButton);

	}

	private void hideComponent(Component c) {
		c.setVisible(false);
		c.setEnabled(false);
	}

	private void unhideComponent(Component c) {
		c.setVisible(true);
		c.setEnabled(true);
	}

	class ImageSlider extends JPanel implements Runnable, ActionListener {
		Constraints gbc = new Constraints();
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
			setPreferredSize(new Dimension(dim.width, dim.height));
			getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"),
					"doEscapeAction");
			getActionMap().put("doEscapeAction", escapeAction);
			setDoubleBuffered(true);
			setLayout(new GridBagLayout());
			init();
			start();

		}

		private void init() {

			settingsButton = new JButton("gfhdhhthdthydtyh");
			settingsButton.addActionListener(this);
			settingsButton.setIcon(new ImageIcon(getClass().getResource(
					"/resource/img/settings.gif")));
			settingsButton.setBorderPainted(false);
			settingsButton.setContentAreaFilled(false);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.set(1, 10, 10, 10, 1, 1, new Insets(dim.height, 130, 80,
					dim.height + 200), GridBagConstraints.NORTHWEST);
			add(settingsButton, gbc);
		}

		public void start() {
			th = new Thread(this);
			th.start();

		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (show != null) {
				show.paint(g);

			}
			;

		}

		@Override
		public void run() {
			System.out.println("run()");

			try {
				while (stop != true) {

					// System.out.println("before thread");

					// System.out.println("after thread");
					Thread.sleep(viewCtrl.getDisplayTime());
					show.moveNext();
					repaint();
					System.out.println("ShowINterface, kaller moveNext()");

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
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent tf) {
				System.out.println("Escape key pressed");
				closeWindow();
			}

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(settingsButton)
					&& (ld == null || !ld.isVisible())) {

				slider.stopClick();
				openLoginBox();
				System.out.println("Check modal");

			}

		}

	}

	public void closeWindow() {
		if (ShowInterface.this != null) {
			slider.stopClick();
			show = null;
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

		}

	}

}
