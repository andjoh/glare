package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import bll.DisplayController;

public class ShowInterface extends JFrame implements ActionListener {
	/**
	 *
	 * @author Andreas J
	 */
	private DisplayController dc;
	private final JButton settingsButton;

	private final JPanel panel;
	Constraints gbc;
	private final ImageShow show;
	private boolean stop = true;
	private ImageSlider slider;

	public ShowInterface(DisplayController displayController) throws IOException {

		dc = displayController;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		settingsButton = new JButton("Test");
		settingsButton.addActionListener(this);
		panel = new JPanel();
		panel.add(settingsButton);
		panel.setBackground(Color.BLACK);
		add(panel, BorderLayout.SOUTH);

		show = new ImageShow(dc);
		System.out.println("kaller imageshow");


		add(show, BorderLayout.CENTER);
		//this.setUndecorated(true);
		setVisible(true);
		System.out.println("setVIsible");



		GraphicsEnvironment.getLocalGraphicsEnvironment().
		getDefaultScreenDevice().setFullScreenWindow(this);
		System.out.println("graphicsEnvironment");
		startClick();
		System.out.println("kaller startClick() / slider");
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
			System.out.println("imageSlider");
			started = true;
//			run();
		}

		@Override
		public void run() {
			System.out.println("run()");
			int i;
			try {
				while(true){
					if (stop != true) {
						System.out.println("before thread");
						Thread.sleep(dc.getDisplayTime());
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
}
