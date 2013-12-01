package unit;

import glare.ClassFactory;
import gui.SettingsFrame;
import java.awt.EventQueue;
import javax.swing.*;
import bll.*;


/*
 * Show SettingsFrame and perform tests regarding hashtags, view mode and display time
 *    - Using Spring to get objects from BLL and DAL
 *    - Using database
 *    
 * Tests Performed:
 * 1. No hashtags in db
 *    a) Add hashtags to db - OK
 *    b) Remove hashtags from db - OK
 * 2. Db containing hashtags
 *    a) Add hashtags to db - OK
 *    b) Remove hashtags from db - OK
 * 3. Random set
 *    a) Set Random true/false - OK
 * 4. Displaytime set
 *    a) Set Displaytime - OK    
 */
public class MainTestSettingsFrameUsingSpringAndDb  extends JFrame{
	SettingsFrame settingsFrame;
	
	public MainTestSettingsFrameUsingSpringAndDb() {

		ViewController vc = (ViewController) ClassFactory.getBeanByName("viewController");
		vc.getSortedList(); 
		settingsFrame = new SettingsFrame(vc);
		boolean suc =  settingsFrame.validationExit();

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

				
				 new MainTestSettingsFrameUsingSpringAndDb();
				
				

			}
		});
	}
}
