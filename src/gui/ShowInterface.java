package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

import bll.*;

public class ShowInterface extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * @author Andreas J
	 */
	private ViewController viewCtrl;
	private JButton settingsButton;
	private LoginDialog ld = null;
	private Dimension dim = null;
	private ImageSlider slider;
	private SettingsFrame settingsFrame;
	private JFrame parent = null;
	private ImageShow show;

	public ShowInterface(ViewController viewCtrl) throws IOException {
		this.dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.parent = this;
		this.viewCtrl = viewCtrl;
		settingsFrame = null;
		slider = new ImageSlider(this.dim);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// setAutoRequestFocus(true);
		requestFocusInWindow();
		getContentPane().add(slider);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			}

			@Override
			public void windowIconified(WindowEvent e) {
				System.out.println("Window  iconified");

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

				System.out.println("Window  deiconified");
			}

		});
		addWindowFocusListener(new WindowAdapter() {

			@Override
			public void windowLostFocus(WindowEvent e) {
				System.out.println("Window lost focus");

			}
		});

		setUndecorated(true);
		pack();
		setVisible(true);
		setFullScreen();
		setResizable(false);

	}

	private void setFullScreen() {
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	public void openSettingsFrame() {
		settingsFrame = new SettingsFrame(viewCtrl);

		boolean suc = settingsFrame.validationExit();
		if (suc) {
			unhideComponent(settingsButton);
			slider.start();
		}

	}

	public void openLoginBox() {
		slider.stopClick();
		hideComponent(settingsButton);
		ld = new LoginDialog(parent);
		boolean suc = ld.getSucceeded();
		if (suc)
			openSettingsFrame();
		else {
			slider.start();

		}

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
		private volatile boolean stop = false;
		private Dimension dim;
		private volatile Thread th;

		private Action escapeAction;

		public ImageSlider(Dimension dim) throws IOException {

			this.dim = dim;
			show = new ImageShow(viewCtrl, (int) dim.getWidth(),
					(int) dim.getHeight());

			setPreferredSize(new Dimension(dim.width, dim.height));
			escapeAction = new EscapeAction();
			getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"),
					"doEscapeAction");
			getActionMap().put("doEscapeAction", escapeAction);
			setBackground(Color.black);
			setDoubleBuffered(true);
			setLayout(new GridBagLayout());
			init();
			start();

		}

		private void init() {
			Constraints gbc = new Constraints();
			settingsButton = new JButton();
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
		public void paint(Graphics g) {
			super.paint(g);
			if (show != null) {
				show.paint(g);

			}
			// uper.paintComponent(g);
		}

		public void stopClick() {
			stop = true;
			try {
				th.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void run() {
			System.out.println("run()");
			stop = false;
			int d = viewCtrl.getDisplayTime() * 1000;
			System.out.println("Display time: " + d);
			try {
				while (stop != true) {

					// System.out.println("before thread");

					// System.out.println("after thread");

					int i = 0;
					System.out.println("IS called :" + i + "times");
					show.moveNext();
					repaint();
					Thread.sleep(d);
					// System.out.println("ShowINterface, kaller moveNext()");

				}
				// th.join();

			} catch (InterruptedException ie) {
				System.out.println("Interrupted slide show...");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		class EscapeAction extends AbstractAction {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent tf) {

				closeWindow();
			}

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(settingsButton)
					&& (ld == null || !ld.isVisible())) {
				openLoginBox();

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
