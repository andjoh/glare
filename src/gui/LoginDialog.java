package gui;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class LoginDialog extends JDialog {

	/**
	 * 
	 * @author Andreas J.
	 */
	private static final long serialVersionUID = 1L;
	private JLabel backgroundImageLabel;
	private JFrame jf;
	private LPanel jp;
	private LoginInputPanel inputpanel;
	private ButtonPanel buttonPanel;
	private Dimension dim;

	LoginDialog logd = this;

	public LoginDialog(JFrame jf) {
		super(jf);
		this.jf = jf;
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.dim = new Dimension((int) (dim.getWidth() / 3),
				(int) dim.getHeight() / 4);
		jp = new LPanel();
		getContentPane().add(jp);
		setSize(this.dim);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		setLocationRelativeTo(this.jf);
		pack();
		setVisible(true);
	}

	/*
	 * Declares GUI objects.
	 */

	class LPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public LPanel() {
			setOpaque(false);
			setPreferredSize(dim);
			setLayout(null);
			init();
			
			add(inputpanel);
			add(buttonPanel);
			add(backgroundImageLabel);

		}

		public void init() {
			inputpanel = new LoginInputPanel(new Dimension(dim.width,
					dim.height * 2 / 3));
			// ButtonPanel properties

			buttonPanel = new ButtonPanel(logd, inputpanel, new Dimension(
					new Dimension(dim.width, dim.height)));
			;

			// backgroundImageLabel properties
			backgroundImageLabel = new JLabel();

			backgroundImageLabel.setIcon(new ImageIcon(getClass().getResource(
					"/resource/img/backgr.jpg")));
			backgroundImageLabel.setIconTextGap(0);
			// backgroundImageLabel.setPreferredSize(dim);
			// /backgroundImageLabel.setBounds(0, 0,dim.width,dim.height);
			backgroundImageLabel.setBounds(0, 0, dim.width, dim.height);

		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
		}

	}

	public boolean getSucceeded() {
		return buttonPanel.getSucceeded();
	}

}
