package unit;

import java.io.IOException;
import bll.*;
import glare.*;
import gui.*;


public class MainTestFromDb {
	/**
	 * USE THIS CLASS TO RETRIEVE PICTUREDATA FROM DB, MAKE PICTURES IN DISPLAYCONTROLLER AND DISPLAY
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) {

		// Test using dummy DatabaseManager and dummy hashtag
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
								
				ViewController vc = (ViewController) ClassFactory.getBeanByName("viewController");

				try {
					ShowInterface showInterface = new ShowInterface(vc);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}