package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class LoginDialogPanel extends JPanel {
	
    private JTextField usernameInputField;
    private Dimension dim;
    private JLabel usernameLabel,passwordLabel;
    private JPasswordField passwordInputField;
    private Constraints gbc;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginDialogPanel( JTextField usernameInputField,
    JLabel usernameLabel,JLabel passwordLabel,
    JPasswordField passwordInputField, Dimension dim) {
		this.usernameInputField=usernameInputField;
		   this.usernameLabel=usernameLabel; 
		   this.passwordLabel=passwordLabel;
		   this.passwordInputField=passwordInputField;
		   this.dim=dim;
        gbc= new Constraints();
		setLayout(new GridBagLayout());
		setBorder(new LineBorder(Color.GRAY));
	
		setConstraints();
		setPreferredSize(dim);
	}
	/*
	 * Sets GUI objects with GB Layout using the Constraint class 
	 * 
	 */
	private void setConstraints() {
                    
		gbc.set(0, 0, 1, 1,0,0);
		add(usernameLabel, gbc);
		gbc.set(1, 0, 2, 1,0,0);
		add(usernameInputField, gbc);
		gbc.set(0, 1, 1, 1,0,0);
		add(passwordLabel, gbc);
		gbc.set(1, 1, 2, 1,0,0);
		add(passwordInputField, gbc);
	}
	public String getUsername() {
		return usernameInputField.getText().trim();
	}

	public String getPassword() {
		return new String(passwordInputField.getPassword());
	}
	

}