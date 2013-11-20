package unit;

import glare.ClassFactory;
import gui.SettingsFrame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import bll.ViewController;

public class MainTestSettingsFrame {

	public static void main(String args[]) {
		/*
		 * Set the Nimbus look and feel
		 */
		try {
			for (UIManager.LookAndFeelInfo info : UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(SettingsFrame.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(SettingsFrame.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(SettingsFrame.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(SettingsFrame.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		

		EventQueue.invokeLater(new Runnable() {
			public void run() {

				ViewController vc = (ViewController) ClassFactory.getBeanByName("viewController");

				
				SettingsFrame intfr = new SettingsFrame(vc);
				JFrame fr = new JFrame();
				fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				fr.getContentPane().add(intfr);
				fr.pack();
				fr.setVisible(true);

			}
		});
	}
	
	
	
}
