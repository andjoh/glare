package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class ShowInterface extends JFrame implements ActionListener {
	/**
	 * 
	 * @author Andreas J
	 */

	private JButton settingsButton;

	private JPanel panel;
	Constraints gbc;
	private ImageShow show;
	private boolean stop = true;
	private ImageSlider slider;
	private ImageShow imageShow;
	private LoginDialog loginDialog;

	public ShowInterface(ImageShow imageShow_, LoginDialog loginDialog_) {
		imageShow = imageShow_;
		loginDialog = loginDialog_;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();
		add(panel, BorderLayout.SOUTH);
		add(imageShow, BorderLayout.CENTER);

		/*
		 * <edited out for easier testing> - Andreas J
		 * this.setUndecorated(true);
		 */
		setVisible(true);

		GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().setFullScreenWindow(this);
		startClick();
	}

	public void init() {

		settingsButton = new JButton("Settings");
		settingsButton.addActionListener(this);
		panel = new JPanel();
		panel.add(settingsButton);
		panel.setBackground(Color.BLACK);
		loginDialog.setLocationRelativeTo(this);
		loginDialog.setSize(400, 500);

		loginDialog.setAlwaysOnTop(true);

		loginDialog.setResizable(false);
		loginDialog.pack();

	}

    private final JPanel panel;
    Constraints gbc;
    private final ImageShow show;
    private boolean stop = true;
    private ImageSlider slider;

    public ShowInterface() {
       
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        settingsButton = new JButton("Test");
        settingsButton.addActionListener(this);
        panel = new JPanel();
        panel.add(settingsButton);
        panel.setBackground(Color.BLACK);
        add(panel, BorderLayout.SOUTH);

        show = new ImageShow();
      
  
        add(show, BorderLayout.CENTER);
        //this.setUndecorated(true);
        setVisible(true);
       
      
        
        GraphicsEnvironment.getLocalGraphicsEnvironment().
        getDefaultScreenDevice().setFullScreenWindow(this);
        startClick();
    }

	public void stopClick() {
	
		stop = true;
		slider.stopShow();
	}

	public void openLoginBox() {

		loginDialog.setVisible(true);

	}

	class ImageSlider extends Thread {

    public void openLoginBox() {
        LoginDialog ld = new LoginDialog(this);
        ld.setLocationRelativeTo(null);
        ld.setSize(400, 500);
        ld.pack();
        ld.setVisible(true);
    }

		ImageSlider() {
			started = true;

		}

		@Override
		public void run() {
		
			try {
				while (true) {
					if (stop != false) {
						Thread.sleep(2000);
						show.moveNext();
						repaint();
					}

				}
			} catch (InterruptedException ie) {
				System.out.println("Interrupted slide show...");
			} catch (IOException e) {
		
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
}
