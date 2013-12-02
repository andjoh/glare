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

public class LoginInputPanel extends JPanel {

	private JTextField usernameInputField;
	 private Dimension dim;
	private JLabel usernameLabel, passwordLabel;
	private JPasswordField passwordInputField;
	private Constraints gbc;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginInputPanel(Dimension dim) {
         this.dim=dim;
		gbc = new Constraints();
		setLayout(new GridBagLayout());
		setOpaque(false);
		init();
		setBounds(0,0,this.dim.width,dim.height);
		setConstraints();
		
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}

	private void init() {
		usernameLabel = new JLabel("Username: ");
		usernameLabel.setFont(new Font("Tahoma", 0, 14));
		usernameLabel.setForeground(new Color(255, 255, 255));
		//
		passwordLabel = new JLabel("Password: ");
		passwordLabel.setFont(new Font("Tahoma", 0, 14));
		passwordLabel.setForeground(new Color(255, 255, 255));
		//
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