package unit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dal.*;
import bll.*;
import glare.ClassFactory;
import gui.*;
import resources.*;

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
								
				DisplayController dc = (DisplayController) ClassFactory.getBeanByName("displayController");

				try {
					ShowInterface showInterface = new ShowInterface(dc);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}