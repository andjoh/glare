package unit;

import glare.ClassFactory;
import gui.SettingsFrame;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.*;

import resources.DatabaseManagerDummy;
import dal.DatabaseHandler;

import bll.*;

/*
 * Show SettingsFrame and perform tests regarding hashtags, view mode and display time
 *    
 * Tests Performed:
 * 1. No hashtags in db
 *    a) Add hashtags to db
 *    b) Remove hashtags from db
 * 2. Db containing hashtags
 *    a) Add hashtags to db
 *    b) Remove hashtags from db
 * 3. Random set
 *    a) Set Random true/false
 * 4. Displaytime set
 *    a) Set Displaytime 
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
		hashtagsDummy.add("sun");
		hashtagsDummy.add("lol");
		hashtagsDummy.add("sad");
		hashtagsDummy.add("happy");
		hashtagsDummy.add("party");
		hashtagsDummy.add("cat");
		hashtagsDummy.add("hello");
		hashtagsDummy.add("christmas");
		hashtagsDummy.add("football");
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
