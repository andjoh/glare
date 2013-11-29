package gui;

import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class LoginInputPanel extends JPanel {

	private JTextField usernameInputField;
	// private Dimension dim;
	private JLabel usernameLabel, passwordLabel;
	private JPasswordField passwordInputField;
	private Constraints gbc;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginInputPanel(int i) {

		gbc = new Constraints();
		setLayout(new GridBagLayout());
		setBorder(new LineBorder(Color.GRAY));

		// setPreferredSize(dim);
		init();
		setConstraints();
	}

	private void init() {
		usernameLabel = new JLabel("Username: ");
		passwordLabel = new JLabel("Password: ");
		usernameInputField = new JTextField(20);
		passwordInputField = new JPasswordField(20);

	}

	public LoginInputPanel() {

	}

	/*
	 * Sets GUI objects with GB Layout using the Constraint class
	 */
	private void setConstraints() {

		gbc.set(0, 0, 1, 1, 0, 0);
		add(usernameLabel, gbc);
		gbc.set(1, 0, 2, 1, 0, 0);
		add(usernameInputField, gbc);
		gbc.set(0, 1, 1, 1, 0, 0);
		add(passwordLabel, gbc);
		gbc.set(1, 1, 2, 1, 0, 0);
		add(passwordInputField, gbc);
	}

	public String getUsername() {
		return usernameInputField.getText().trim();
	}

	public String getPassword() {
		return new String(passwordInputField.getPassword());
	}

	public void resetFields() {
		usernameInputField.setText("");
		passwordInputField.setText("");
	}

}