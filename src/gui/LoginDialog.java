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
	private LoginDialogPanel inputpanel;
	private ButtonPanel buttonPanel;
	private JButton okButton, cancelButton;
	private JPasswordField passwordInputField;
	private JTextField usernameInputField;
	private Dimension dim, totalsize;
	private JLabel usernameLabel, passwordLabel;
	private boolean succeeded;

	public LoginDialog(JFrame jf) {
		super(jf, "", true);
		this.jf = jf;
		// setModal(true);
		dim= Toolkit.getDefaultToolkit().getScreenSize();
		totalsize= new Dimension((int)(dim.getWidth()/4),(int)dim.getHeight()/7);
		init();
		setLayout(new BorderLayout());
		add(inputpanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.PAGE_END);
	
		pack();
		//setResizable(false);
		setAlwaysOnTop(succeeded);
		requestFocusInWindow();
		setLocationRelativeTo(jf);
		setVisible(true);

	}

	/*
	 * Declares GUI objects.
	 */
	public void init() {
		usernameLabel = new JLabel("Username: ");
		passwordLabel = new JLabel("Password: ");
		usernameInputField = new JTextField(20);
		passwordInputField = new JPasswordField(20);
		inputpanel = new LoginDialogPanel(usernameInputField, usernameLabel,
				passwordLabel, passwordInputField,new Dimension((int)totalsize.getWidth(),(int)totalsize.getHeight()*2/3));
		// ButtonPanel properties

		buttonPanel = new ButtonPanel(new Dimension((int)totalsize.getWidth(),(int)totalsize.getHeight()*1/3));
		// ok butto
		

	}

	
	

	public boolean getSucceeded() {
		return succeeded;
	}
	class ButtonPanel extends JPanel implements ActionListener{
		private Dimension dim;
		 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private  Action enterAction;
		
		public ButtonPanel(Dimension dim){
			setBackground(Color.green);
			//setLayout(new FlowLayout());
			this.dim=dim;
			setPreferredSize(dim);
	    setFocusable(true);
		getInputMap().put( KeyStroke.getKeyStroke( "ENTER" ),
		                "doEnterAction" );
		getActionMap().put( "doEnterAction", enterAction );
		 init();
			
		}
		private void init(){
			okButton = new JButton("Login");
			okButton.addActionListener(this);

			cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(this);
			
		add(okButton);
			add(cancelButton);
			enterAction= new EnterAction();
			
		}
		
		
		
		private void tryToLogin() {

		if (LoginData.authenticate(inputpanel.getUsername(),
				inputpanel.getPassword())) {
			succeeded = true;
			setVisible(false);
		} else {
			JOptionPane.showMessageDialog(LoginDialog.this, "Wrong credentials", "Login",
					JOptionPane.ERROR_MESSAGE);

			usernameInputField.setText("");
			passwordInputField.setText("");
			succeeded = false;

		}
	}
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Object jc = e.getSource();
			if (jc.equals(okButton)){
				tryToLogin();
				 System.out.println( "The Enter key has been pressed." );
			}
			else if (jc.equals(cancelButton))
				dispose();
		}
	      class EnterAction extends AbstractAction
		    {
		        public void actionPerformed( ActionEvent tf )
		        {
		            
		           
		        	 System.out.println( "The Enter key has been pressed." );
		        
		           okButton.doClick();
		            
		        } 
		        
		    }

		
		
		
	}

}
