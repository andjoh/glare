package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

;

class ButtonPanel extends JPanel {
	private boolean succeeded = false;
	private Dimension dim;
	private LoginInputPanel input;
	private LoginDialog ld;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton okButton, cancelButton;

	public ButtonPanel(final LoginDialog ld, LoginInputPanel input) {
		super();
		this.ld = ld;
		this.input = input;
		setBackground(Color.green);
		setPreferredSize(dim);
		setFocusable(true);
		init();

	}

	private void init() {

		this.add(new JButton(new AbstractAction("Login") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tryToLogin()) {
					ld.setVisible(false);
					ld.dispatchEvent(new WindowEvent(ld,
							WindowEvent.WINDOW_CLOSING));
				}
			}
		}));

		this.add(new JButton(new AbstractAction("Cancel") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				ld.setVisible(false);
				ld.dispatchEvent(new WindowEvent(ld, WindowEvent.WINDOW_CLOSING));
			}
		}));

	}

	private boolean tryToLogin() {
		if (LoginData.authenticate(input.getUsername(), input.getPassword())) {
			succeeded = true;

		} else {
			JOptionPane.showMessageDialog(ld, "Wrong credentials", "Login",
					JOptionPane.ERROR_MESSAGE);
			input.resetFields();
			succeeded = false;

		}
		return succeeded;

	}

	public boolean getSucceeded() {
		return succeeded;
	}

}
