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
		setAutoRequestFocus(true);
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
		hideComponent(settingsButton);
		ld = new LoginDialog(parent);
		boolean suc = ld.getSucceeded();
		if (suc) {
			openSettingsFrame();
		} else
			unhideComponent(settingsButton);
		slider.start();

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
		private Dimension dim;
		private Thread th;

		private Action escapeAction;

		public ImageSlider(Dimension dim) throws IOException {

			this.dim = dim;
			show = new ImageShow(viewCtrl, (int) dim.getWidth(),
					(int) dim.getHeight());
			escapeAction = new EscapeAction();
			setPreferredSize(new Dimension(dim.width, dim.height));
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
			settingsButton = new JButton("gfhdhhthdthydtyh");
			settingsButton.addActionListener(this);
			// settingsButton.setIcon(new ImageIcon(getClass().getResource(
			// "/resource/img/settings.gif")));
			// settingsButton.setBorderPainted(false);
			// settingsButton.setContentAreaFilled(false);
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
			super.paintComponent(g);
		}

		@Override
		public void run() {
			System.out.println("run()");
			stop = false;
			int d = viewCtrl.getDisplayTime();
			System.out.println("Display time: " + d);
			try {
				while (stop != true) {

					// System.out.println("before thread");

					// System.out.println("after thread");

					Thread.sleep(d);
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
