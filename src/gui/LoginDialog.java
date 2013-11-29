package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class LoginDialog extends JDialog {

	/**
	 * 
	 * @author Andreas J.
	 */
	private static final long serialVersionUID = 1L;

	private JFrame jf;
	private JPanel jp;
	private LoginInputPanel inputpanel;
	private ButtonPanel buttonPanel;
	private Dimension dim, totalsize;

	LoginDialog logd = this;

	public LoginDialog(JFrame jf) {
		super(jf, "", true);
		this.jf = jf;
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		totalsize = new Dimension((int) (dim.getWidth() / 4),
				(int) dim.getHeight() / 7);
		init();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		setResizable(false);
		setAlwaysOnTop(true);
		requestFocusInWindow();
		setLocationRelativeTo(this.jf);
		pack();
		jf.pack();
		setVisible(true);

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("Window is closing");
			}
		});

	}

	/*
	 * Declares GUI objects.
	 */
	public void init() {
		inputpanel = new LoginInputPanel(1);
		inputpanel.setPreferredSize(new Dimension(totalsize.width,
				totalsize.height * 2 / 3));
		// ButtonPanel properties

		buttonPanel = new ButtonPanel(logd, inputpanel);
		buttonPanel.setPreferredSize(new Dimension(totalsize.width,
				totalsize.height * 1 / 3));

		// add both panels to a panel

		jp = new JPanel();
		jp.setPreferredSize(totalsize);
		jp.setLayout(new BorderLayout());
		jp.add(inputpanel, BorderLayout.CENTER);
		jp.add(buttonPanel, BorderLayout.PAGE_END);
		setContentPane(jp);
	}

	public boolean getSucceeded() {
		return buttonPanel.getSucceeded();
	}

}
