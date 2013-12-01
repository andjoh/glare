package gui;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

;

class ButtonPanel extends JPanel implements ActionListener{
	private boolean succeeded = false;
	private Dimension dim;
	private LoginInputPanel input;
	private LoginDialog ld;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton okButton, cancelButton;

	public ButtonPanel(final LoginDialog ld, LoginInputPanel input,Dimension dim) {
		this.ld = ld;
		this.input = input;	
		this.dim =dim;
		this.setBounds(0,this.dim.height*2/3,dim.width,dim.height*1/3);
	    
		setOpaque(false);
		setFocusable(true);
		init();

	}

	private void init() {
		okButton= new JButton("Login");
		cancelButton= new JButton("Cancel");
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
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	public boolean getSucceeded() {
		return succeeded;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
            if(e.getSource().equals(okButton)){
            	
            	if (tryToLogin()) {
					ld.setVisible(false);
					ld.dispatchEvent(new WindowEvent(ld,
							WindowEvent.WINDOW_CLOSING));
				} else {
					JOptionPane.showMessageDialog(ld, "Wrong credentials", "Login",
							JOptionPane.ERROR_MESSAGE);
					input.resetFields();
					succeeded = false;

				}
            }
            else if(e.getSource().equals(cancelButton)){
            	
            	ld.setVisible(false);
				ld.dispatchEvent(new WindowEvent(ld, WindowEvent.WINDOW_CLOSING));
            }
		
	}

}
