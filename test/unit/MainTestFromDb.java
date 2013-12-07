package unit;

import glare.ClassFactory;
import gui.SettingsFrame;
import gui.ShowInterface;

import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import bll.ViewController;


public class MainTestFromDb {
	/**
	 * USE THIS CLASS TO RETRIEVE PICTUREDATA FROM DB, MAKE PICTURES IN DISPLAYCONTROLLER AND DISPLAY
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
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
		// Test using dummy DatabaseManager and dummy hashtag
		SwingUtilities.
		invokeLater(new Runnable() {
			@Override
			public void run() {
				ViewController vc  = (ViewController) ClassFactory.getBeanByName("viewController");
			

				try {
					@SuppressWarnings("unused")
					ShowInterface  showInterface= new ShowInterface(vc);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}