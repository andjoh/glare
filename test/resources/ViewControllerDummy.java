package resources;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

import bll.PictureController;
import bll.SettingsPicture;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

import dal.*;

/**
 * @author Simen Sollie, Kristine Svaboe, Petter Austerheim
 * @since 2013-11-04
 */

public class ViewControllerDummy {

	/**
	 * Manage list with pictures
	 */
	


	private List<PictureData> sortedPictureList;
	private List<PictureData> randomPictureList;
	private List<PictureData> pictureDataList;
	private Set<String> hashtags;
	private int row=5,col=20;
	private boolean isRandom;
	private int displayTime;
	private String[] urls = new String[]{};

	public ViewControllerDummy(PictureController picCtrl, DatabaseManager dbMan){
		
		sortedPictureList = new ArrayList<PictureData>();
		
		// Default settings
		isRandom    = false;
		displayTime = 1000;
	}
	
	public BufferedImage getCurrentPicture() throws IOException{
		if ( sortedPictureList.isEmpty() ) {
			System.out.println("Henter ny liste");
			getSortedList();
			System.out.println("Ferdig å hente liste");
		}
		
		PictureData p;

		if ( isRandom ) {
			p = randomPictureList.remove(0);
			sortedPictureList.remove(p);
		}
		else {
			p = sortedPictureList.remove(0);
			randomPictureList.remove(p);
		}
		
		return getBufImage(p.getUrlStd());
	}

	public void getSortedList() {
		//pictureDataList   = picCtrl.getSortedPictureData();
		sortedPictureList = new ArrayList<PictureData>(pictureDataList);
		randomPictureList = new ArrayList<PictureData>(sortedPictureList);
		pictureDataList   = new ArrayList<PictureData>(sortedPictureList);
		Collections.shuffle(randomPictureList);
	}
	
	private BufferedImage getBufImage(String path) throws IOException{
		URL url = this.getClass().getResource(path);
          BufferedImage tmp = null;
		try {

			tmp = ImageIO.read(url);

		} catch (IOException ex) {

		}
		return tmp;
	}
 
	public List<SettingsPicture> getSettingsPictures() {
		System.out.println("ViewController: getSettingsPictures");
		
		List<SettingsPicture> settingsPictures = new ArrayList<SettingsPicture>();

		String id, url;

		for (int i=0;i<row*col;i++) {
			
			id  = Integer.toString(Integer.valueOf((int)Math.random()*98111+33311));
			url ="/resource/img/"+ i%5+".png";
			
			try {
				settingsPictures.add(new SettingsPicture(id, getBufImage(url)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		return settingsPictures;
	}
	
	public  List<List<SettingsPicture>> getSettingsPicturesAs2DList()  {
	
		List<SettingsPicture> df=getSettingsPictures();
		List<List<SettingsPicture>> settingsPictures =new ArrayList<List<SettingsPicture>>();
			int startindex=0;
			for (int c=0;c<100;c+=5){
				
			settingsPictures.add(c/5,new ArrayList<SettingsPicture>(df.subList(c,c+5)));
			System.out.println("Rad: "+c/row+"startindex: "+startindex+"stopindex"+c/5+5);
			startindex+=5;
			}
		return settingsPictures;
	}
	




	public void updateHashtags(Set<String> hashtagList) {

		// Check if hashtags have been add
		Set<String> hashtagAdded   = new HashSet<String>();
		for ( String ht : hashtagList ) {
			if ( !hashtags.contains(ht) ) {
				hashtagAdded.add(ht);
			}		
		}
				
		// Check if hashtags have been deleted
		Set<String> hashtagDeleted = new HashSet<String>();
		for ( String ht : hashtags ) {
			if ( !hashtagList.contains(ht) ) {
				hashtagDeleted.add(ht);
			}		
		}
				
		// Update db regarding hashtags deleted
		// Delete pictures that are connected to these, and only these, hashtags
		if ( !hashtagDeleted.isEmpty() ) {
				
		}
		
		// Update db with new hashtags
		if ( !hashtagAdded.isEmpty() ) {
		;
		}	
		
		// Set the new list as current list
		hashtags = hashtagList;
	}

	public boolean isRandom() {
		return isRandom;
	}

	public void setRandom(boolean isRandom) {
		this.isRandom = isRandom;
	}
	
	public int getDisplayTime() {
		return displayTime / 1000;
	}

	public void setDisplayTime(int displayTime) {
		this.displayTime = displayTime * 1000;
	}	
}


