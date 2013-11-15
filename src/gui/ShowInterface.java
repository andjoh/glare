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

	private final JButton settingsButton;

	private final JPanel panel;
	Constraints gbc;
	private final ImageShow show;
	private boolean stop = true;
	private ImageSlider slider;

	public ShowInterface() throws IOException {

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
		LoginDialog ld = new LoginDialog();
		ld.setLocationRelativeTo(null);
		ld.setSize(400, 500);
		ld.pack();
		ld.setVisible(true);
	}


	class ImageSlider extends Thread {

		boolean started;
		int size;
		ImageSlider() {
			started = true;

		}

		@Override
		public void run() {
			int i;
			try {
				while(true){
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
