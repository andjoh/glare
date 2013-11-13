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

		settingsButton = new JButton("Test");
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

		loginDialog.setVisible(true);

	}

	class ImageSlider extends Thread {

		boolean started;
		int size;

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
}
