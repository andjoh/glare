package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
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
	private static final long serialVersionUID = 1L;
	private JButton okButton, cancelButton; // buttons: ok,cancel option
	private JPasswordField passwordInputField; // field to input password
	private JTextField usernameInputField;  // field to input username
	private DocumentListener listenToTextFields; // listen for changes in input and userfield

	/**
	 * @param ld
	 * @param input
	 * @param dim
	 */
	public ButtonPanel(final LoginDialog ld, LoginInputPanel input,
			Dimension dim) {
		this.ld = ld;
		this.input = input;
		this.usernameInputField = input.getUserField();
		this.passwordInputField = input.getPasswordField();
		this.dim = dim;
		// sets location and size
		// baes on LoginDialogs size
		
		this.setBounds(0, this.dim.height * 2 / 3, dim.width,
				dim.height * 1 / 3);
		init();
		// sets inputmap used to listen for input by keyboard
		//  Enter hotkey to click okButton
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");

		getActionMap().put("enter", new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (okButton.isEnabled())
					okButton.doClick();
			}
		});
	
		setOpaque(false);
		setFocusable(true);
		this.requestFocusInWindow();
		

	}

	/**
	 * Method called by constructor to initialize 
	 * Components
	 */
	private void init() {
		// listener that listens for changes in the input fields
	    // used to  determine when to check if the okButton
		// should be enabled
		
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

			}
		};
		// add document listener to both fields
		usernameInputField.getDocument().addDocumentListener(listenToTextFields);
		passwordInputField.getDocument().addDocumentListener(listenToTextFields);
        // set properties of okButton
		okButton = new JButton("Login");
		okButton.setEnabled(false);
		okButton.addActionListener(this);
		add(okButton);
		// set properties to cancelButton
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		add(cancelButton);

	}

	/**
	 * Call method to verify login data
	 * <p> Compares entered data with hardcoded data
	 * <p>
	 * @return
	 */
	private boolean tryToLogin() {
		if (LoginData.checkLoginInfo(input.getUsername(), input.getPassword())) {
			succeeded = true;

		}
		return succeeded;

	}

	/**
	 * Check if the okButton should be enabled
	 * <p> 
	 * If both fields contain text
	 * The button will be enabled
	 * <p>
	 */
	public void canEnter() {
		if (input.checkFieldsFull()) {
			okButton.setEnabled(true);
		} else
			okButton.setEnabled(false);

	}

	/**
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public boolean getSucceeded() {
		return succeeded;
	}

	

	/**  
	 * ActionListener for OK and Cancel buttons
	 * non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// If okButton is pressed
		if (e.getSource().equals(okButton)) {
           // If successfull data enetered
		   // Close dialog
			
			if (tryToLogin()) {
				succeeded = true;
				ld.setVisible(false);
				ld.dispatchEvent(new WindowEvent(ld, WindowEvent.WINDOW_CLOSING));
			}
			//  If unsuccesfull clear fields and 
			//  show error message
			else {
				JOptionPane.showMessageDialog(ld, "Wrong credentials", "Login",
						JOptionPane.ERROR_MESSAGE);
				input.resetFields();
				succeeded = false;

			}
			
		}  
		// If cancelled, close dialog
		else if (e.getSource().equals(cancelButton)) {
			ld.setVisible(false);
			ld.dispatchEvent(new WindowEvent(ld, WindowEvent.WINDOW_CLOSING));
		}

	}
	
	class EnterAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		/**
		 * If user press Enter, call method to click okButton
		 * (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent tf) {
			okButton.doClick();
		}

	}

}
