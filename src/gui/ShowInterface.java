package gui;

import glare.ClassFactory;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import bll.*;

public class ShowInterface extends JFrame implements ActionListener {
	/**
	 *
	 * @author Andreas J
	 */
	private ViewController viewCtrl;
	private final JButton settingsButton;
	private LoginDialog ld;
	private final JPanel panel;
	Constraints gbc;
	private final ImageShow show;
	private boolean stop = true;
	private ImageSlider slider;

	public ShowInterface(ViewController viewCtrl) throws IOException {

		this.viewCtrl = viewCtrl;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		settingsButton = new JButton("Test");
		settingsButton.addActionListener(this);
		panel = new JPanel();
		panel.add(settingsButton);
		panel.setBackground(Color.BLACK);
		add(panel, BorderLayout.SOUTH);
       
		show = new ImageShow(viewCtrl);
		System.out.println("kaller imageshow");
        setPreferredSize(new Dimension(1024,800));
   
		add(show, BorderLayout.CENTER);
		//this.setUndecorated(true);
		addKeyListener(new keyInputAdapter());
		setVisible(true);
		System.out.println("setVIsible");



		setFullScreen();
		System.out.println("graphicsEnvironment");
		startClick();
		System.out.println("kaller startClick() / slider");
		pack();
	}
	private void setFullScreen(){
		GraphicsEnvironment.getLocalGraphicsEnvironment().
		getDefaultScreenDevice().setFullScreenWindow(this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(settingsButton)) {
			stopClick();
			openLoginBox();
		}
	}

	private void startClick() {
		settingsButton.setIcon(new ImageIcon("/img/pause.png"));
		stop = false;
		slider = new ImageSlider();
		slider.start();
	}

	public void stopClick() {
		settingsButton.setIcon(new ImageIcon("/img/play.png"));
		stop = true;
		slider.stopShow();
	}

	public void openLoginBox() {
		
		boolean loggedIn= new LoginDialog(this).getSucceeded();
		System.out.println("Login return equals"+loggedIn);
		if(loggedIn)openSettingsFrame();
	}
	
	private void openSettingsFrame() {
		System.out.println("Tries to open settingsframe");
		
		
		SettingsFrame intfr = new SettingsFrame(viewCtrl);
		getContentPane().add(intfr);
		//pack();
		
	}
	class keyInputAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent evt)  {
			if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
				System.exit(0); 
			}
		}
	}

	class ImageSlider extends Thread {

		boolean started;
		int size;
		ImageSlider() {
			System.out.println("imageSlider");
			started = true;
//			run();
		}

		@Override
		public void run() {
			System.out.println("run()");
		
			try {
				while(true){
					if (stop != true) {
						System.out.println("before thread");
						Thread.sleep(viewCtrl.getDisplayTime());
						System.out.println("after thread");
						show.moveNext();
						repaint();
						System.out.println("ShowINterface, kaller moveNext()");
					}

				}
			} catch (InterruptedException ie) {
				System.out.println("Interrupted slide show...");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void stopShow() {
			started = false;
		}

	}

	@Override
	public void paintComponents(Graphics g) {
		if (show != null) {
			show.paint(g);
		}

	
	
}
