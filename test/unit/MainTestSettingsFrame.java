package unit;

import gui.SettingsFrame;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import resources.DatabaseManagerDummy;
import bll.PictureController;
import bll.ViewController;
import dal.DatabaseHandler;

/**
 * Show SettingsFrame and perform tests regarding hashtags, view mode and display time
 */
public class MainTestSettingsFrame  {
	SettingsFrame settingsFrame;
	public MainTestSettingsFrame() {

		// DUMMY CHECK
		DatabaseHandler dbHandler     = new DatabaseHandler();
		DatabaseManagerDummy dbManDum = new DatabaseManagerDummy(dbHandler);
		PictureController picCtrl     = new PictureController(dbManDum);

		// Do some dummy stuff
		List<String> sourcesDummy;
		Set<String> hashtagsDummy;

		// Set dummy sources
		sourcesDummy = new ArrayList<String>();
		sourcesDummy.add("instagram");
		sourcesDummy.add("twitter");

		// Set test data for source and hashtag
		hashtagsDummy = new HashSet<String>();
		hashtagsDummy.add("winter");
		hashtagsDummy.add("raskebriller");

		// Set dummy sources and hashtag
		dbManDum.setSources(sourcesDummy);
		dbManDum.setHashtags(hashtagsDummy);

		// Run test
		picCtrl.getNewPictureData();
		ViewController vc = new ViewController(dbManDum);
		vc.getSortedList();
		settingsFrame = new SettingsFrame(vc);
	}

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
				new MainTestSettingsFrame();
			}
		});
	}
}