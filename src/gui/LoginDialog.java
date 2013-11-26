package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class LoginDialog extends JDialog implements ActionListener {

	/**
	 *
	 * @author Andreas J.
	 */
	private static final long serialVersionUID = 1L;

	private JFrame jf;
	private LoginPanel panel;

	private Constraints gbc;
	private JPanel buttonPanel;
	private JButton okButton, cancelButton;
	private JPasswordField passwordInputField;
	private JTextField usernameInputField;
	private JLabel usernameLabel, passwordLabel;
	private boolean succeeded;

	public LoginDialog(JFrame jf) {
		this.jf = jf;
		setModal(true);
		setSize(200, 400);
		init();
		add(panel, BorderLayout.CENTER);
	    add(buttonPanel, BorderLayout.PAGE_END);
         pack();
         //setAlwaysOnTop(true);
 		setLocationRelativeTo(jf);
 		setVisible(true);
	
	}
	/*Declares GUI objects.
	 * 
	 *  
	 */
	public void init(){
		panel = new LoginPanel();
		buttonPanel = new JPanel();
		okButton = new JButton("Login");

		okButton.addActionListener(this);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		buttonPanel = new JPanel();
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);


	}
	/*
	 * Returns username typed by user (without spaces)
	 */
	public String getUsername() {
		return usernameInputField.getText().trim();
	}

	public String getPassword() {
		return new String(passwordInputField.getPassword());
	}
	/*
	 * 
	 */
	public boolean isSucceeded() {
		return succeeded;
	}
	/*
	 *  If user has clicked cancel: close Dialog.
	 *  If user has clicked OK: call class to check info
	 * */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object jc= e.getSource();
		if (jc.equals(okButton))tryToLogin();
		else if (jc.equals(cancelButton))setVisible(false);
	}
	/* Calls method to compare provided login information with the hardcoded values.
	 * If correct: closes dialog box. 
	 * If fail: opens up a messageDialog with error message.
	 * 
	 */
	public void tryToLogin() {

		if (LoginData.authenticate(getUsername(), getPassword())) {
			succeeded = true;
			setVisible(false);
		} else {
			JOptionPane.showMessageDialog(LoginDialog.this,
					"Wrong credentials", "Login", JOptionPane.ERROR_MESSAGE);

			usernameInputField.setText("");
			passwordInputField.setText("");
			succeeded = false;

		}
	}
	
	public boolean getSucceeded() {
		return succeeded;
	}

	/* GUI for Login Panel.
	 * The Text and Password fields. 
	 * 
	 * 
	 */
	class LoginPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public LoginPanel() {

			setLayout(new GridBagLayout());
			setBorder(new LineBorder(Color.GRAY));
			init();
			setConstraints();

		}

		private void init() {
			gbc = new Constraints();
			gbc.fill = GridBagConstraints.HORIZONTAL;
			usernameLabel = new JLabel("Username: ");
			passwordLabel = new JLabel("Password: ");
			usernameInputField = new JTextField(20);
			passwordInputField = new JPasswordField(20);
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

	}
}
