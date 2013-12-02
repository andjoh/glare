package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
class ButtonPanel extends JPanel implements ActionListener {
	private boolean succeeded = false;
	private Dimension dim;
	private LoginInputPanel input;
	private LoginDialog ld;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton okButton, cancelButton;
	private Action enterAction;
	private JPasswordField passwordInputField;
	private JTextField usernameInputField;
	private DocumentListener listenToTextFields;

	public ButtonPanel(final LoginDialog ld, LoginInputPanel input,
			Dimension dim) {
		this.ld = ld;
		this.input = input;
		this.usernameInputField=input.getUserField();
		this.passwordInputField=input.getPasswordField();
		this.dim = dim;
		this.setBounds(0, this.dim.height * 2 / 3, dim.width,
				dim.height * 1 / 3);
		enterAction = new EnterAction();
		getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "doEnterAction");
		getActionMap().put("doEntereAction", enterAction);
		setOpaque(false);
		setFocusable(true);
		//requestFocusInWindow();
		init();

	}

	private void init() {
		listenToTextFields = new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				canEnter();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				canEnter();
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
		
				canEnter();
			}
		};
		usernameInputField.getDocument().addDocumentListener(	listenToTextFields);
		passwordInputField.getDocument().addDocumentListener(	listenToTextFields);
		
		
		okButton = new JButton("Login");
		okButton.setEnabled(false);
		cancelButton = new JButton("Cancel");
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		add(okButton);
		add(cancelButton);

	}

	private boolean tryToLogin() {
		if (LoginData.authenticate(input.getUsername(), input.getPassword())) {
			succeeded = true;

		}
		return succeeded;

	}
	public void canEnter(){
		if(input.checkFieldsFull()){
			okButton.setEnabled(true);
		}else okButton.setEnabled(false);
		
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public boolean getSucceeded() {
		return succeeded;
	}

	class EnterAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent tf) {
			okButton.doClick();
			System.out.println("Pressed enter");
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(okButton)) {

			if (tryToLogin()) {
				ld.setVisible(false);
				ld.dispatchEvent(new WindowEvent(ld, WindowEvent.WINDOW_CLOSING));
			} else {
				JOptionPane.showMessageDialog(ld, "Wrong credentials", "Login",
						JOptionPane.ERROR_MESSAGE);
				input.resetFields();
				succeeded = false;

			}
		} else if (e.getSource().equals(cancelButton)) {

			ld.setVisible(false);
			ld.dispatchEvent(new WindowEvent(ld, WindowEvent.WINDOW_CLOSING));
		}

	}

}
