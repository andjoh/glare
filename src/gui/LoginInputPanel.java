package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * @author Andreas Johnstad
 *
 */
public class LoginInputPanel extends JPanel {

	private JTextField usernameInputField; // field to enter username
	private Dimension dim;  // dimensions for this panel
	private JLabel usernameLabel, passwordLabel; // labels 
	private JPasswordField passwordInputField; // field to enter password
	private Constraints gbc; // object used for setting layout constraints
	private static final long serialVersionUID = 1L;

	/**
	 * @param dim
	 */
	public LoginInputPanel(Dimension dim) {
		this.dim = dim;
		gbc = new Constraints();
		setLayout(new GridBagLayout());
		setOpaque(false);
		init();
		setBounds(0, 0, this.dim.width, dim.height);
		setConstraints();

	}

	/**
	 *  (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	/**
	 * Method called by constructor to initialize components
	 */
	private void init() {
		// usernameLabel properties
		usernameLabel = new JLabel("Username: ");
		usernameLabel.setFont(new Font("Tahoma", 0, 14));
		usernameLabel.setForeground(new Color(255, 255, 255));
		// passwordLabel properties
		passwordLabel = new JLabel("Password: ");
		passwordLabel.setFont(new Font("Tahoma", 0, 14));
		passwordLabel.setForeground(new Color(255, 255, 255));
		// usernameInputField properties
		usernameInputField = new JTextField(20);
		// passwordInputField properties
		passwordInputField = new JPasswordField(20);

	}

	/**
	 * @return  // boolean indicating if both fields contain text
	 */
	public boolean checkFieldsFull() {

		if (!getUsername().isEmpty() && !getPassword().isEmpty()) {

			return true;
		}
		return false;

	}

	public JTextField getUserField() {
		return usernameInputField;
	}

	public JPasswordField getPasswordField() {
		return passwordInputField;

	}

	/**
	 * Sets GUI objects with GridBag Layout using the Constraint class
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

	/**
	 * @return   // data currently in the username field
	 */
	public String getUsername() {
		return usernameInputField.getText().trim();
	}

	/**
	 * @return  //data currently in the passwordfield
	 */
	public String getPassword() {
		return new String(passwordInputField.getPassword());
	}

	/**
	 * Clears input fields
	 */
	public void resetFields() {
		usernameInputField.setText("");
		passwordInputField.setText("");
	}

}