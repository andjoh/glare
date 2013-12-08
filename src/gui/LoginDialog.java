package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Andreas Johnstad
 * 
 */
public class LoginDialog extends JDialog {

	/**
	 * 
	 * @author Andreas J.
	 */
	private static final long serialVersionUID = 1L;
	private JLabel backgroundImageLabel; // label to contain background image
	private JFrame parent; // the parent
	private LPanel jp; // panel tp contain inputpanel and buttonpanel
	private LoginInputPanel inputpanel; // panel for text input
	private ButtonPanel buttonPanel; // panel for ok and cancel button
	private Dimension dim; // dimensions of this dialog

	LoginDialog logd = this;

	/**
	 * @param parent
	 */
	public LoginDialog(JFrame parent) {
		super(parent);
		this.parent = parent;
		// sets dim to a portion of the screen dimensions
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.dim = new Dimension((int) (dim.getWidth() / 3), (int) dim.getHeight() / 4);

		jp = new LPanel();
		getContentPane().add(jp);

		// set properties
		setSize(this.dim);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		setLocationRelativeTo(this.parent);
		setUndecorated(true);
		pack();
		setResizable(false);
		setVisible(true);

	}

	/**
	 * @author Andreas Johnstad
	 * 
	 */

	class LPanel extends JPanel {

		/**
		 * Panel to contain a LoginInputPanel and a LoginButtonPanel
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor
		 */
		public LPanel() {
			// set properties
			setOpaque(false);
			setPreferredSize(dim);
			setLayout(null);
			setAutoRequestFocus(true);
			init();
			// add subpanels and backgroundImageLabel
			add(inputpanel);
			add(buttonPanel);
			add(backgroundImageLabel);

		}

		/**
		 * Method called by constructor to initialize components
		 */
		public void init() {
			inputpanel = new LoginInputPanel(new Dimension(dim.width, dim.height * 2 / 3));
			// ButtonPanel properties

			buttonPanel = new ButtonPanel(logd, inputpanel, new Dimension(new Dimension(dim.width, dim.height)));

			// backgroundImageLabel properties
			backgroundImageLabel = new JLabel();
			backgroundImageLabel.setIcon(new ImageIcon(getClass().getResource("/resource/img/backgr.jpg")));
			backgroundImageLabel.setIconTextGap(0);
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
