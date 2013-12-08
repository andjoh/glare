package glare;

import java.io.IOException;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import bll.*;
import gui.*;

/**
 * The application glare is started from this class
 *  - GUI that displays the pictures
 *  - ThreadScheduler to get new picture data from Instagram and Twitter
 * 
 * @author Petter Austerheim
 *
 */
public class Main {
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		
		// Prepare for start display
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
		
		SwingUtilities.
		invokeLater(new Runnable() {
			@Override
			public void run() {
				ViewController vc  = (ViewController) ClassFactory.getBeanByName("viewController");
				ShowInterface showInterface = null;

				try {
					showInterface = new ShowInterface(vc);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		// Scheduler to get picture data
		ThreadScheduler threadScheduler = new ThreadScheduler();
		threadScheduler.run();
	}
}