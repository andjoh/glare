package unit;

import glare.ClassFactory;
import gui.SettingsFrame;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

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
public class MainTestSettingsFrame extends JFrame implements VetoableChangeListener {
	 SettingsFrame settingsFrame;
	public MainTestSettingsFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1024,800));
		JButton button = new JButton("Open SettingsFrame");
		button.setBounds(200,300,200,150);
	    button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               openSettings();
            }
        });
	    //setLayout(null);
	    getContentPane().add(button);
	    pack();
        setVisible(true);
       SettingsFrame settingsFrame = new SettingsFrame();
		 
		  JDesktopPane desktopPane = new JDesktopPane();
		  getContentPane().add(desktopPane);
		  desktopPane.add(settingsFrame);
	
		  settingsFrame.setSize(800, 600);
		  settingsFrame.setLocation(50, 50);
		  
		  settingsFrame.setVisible(true);
		 
		  JPanel gp = new JPanel();
		  gp.setSize(800, 600);
		  gp.setOpaque(true);
		  gp.setVisible(true);
		  gp.setBackground(java.awt.Color.BLUE);
		  settingsFrame.setGlassPane(gp);
		  settingsFrame.addVetoableChangeListener(this);
		 

	}
	public void openSettings(){
		  settingsFrame = new SettingsFrame();
			 
		  JDesktopPane desktopPane = new JDesktopPane();
		  getContentPane().add(desktopPane);
		  desktopPane.add(settingsFrame);
	
		  settingsFrame.setSize(800, 600);
		  settingsFrame.setLocation(50, 50);
		  
		  settingsFrame.setVisible(true);
		 
		  JPanel gp = new JPanel();
		  gp.setSize(800, 600);
		  gp.setOpaque(true);
		  gp.setVisible(true);
		  gp.setBackground(java.awt.Color.BLUE);
		  settingsFrame.setGlassPane(gp);
		  settingsFrame.addVetoableChangeListener(this);
		 
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

				// ViewController vc = (ViewController)
				// ClassFactory.getBeanByName("viewController");

				// SettingsFrame intfr = new SettingsFrame(vc);
			 new MainTestSettingsFrame();

			}
		});
	}

	@Override
	 public void vetoableChange(PropertyChangeEvent evt)
			  throws PropertyVetoException
			 {
			  if(evt.getPropertyName().equals(JInternalFrame.IS_SELECTED_PROPERTY))
			  {
			   System.err.println("SELECTED");
			   throw new PropertyVetoException("Selected", null);
			  }

			 }

}
