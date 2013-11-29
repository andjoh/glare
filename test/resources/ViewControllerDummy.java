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
 * @author Andreas J, Simen Sollie, Kristine Svaboe, Petter Austerheim
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
	private boolean isRandom;
	private int displayTime;

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
	
	private BufferedImage getBufImage(String path){
		URL url = this.getClass().getResource(path);
          BufferedImage tmp = null;
		try {

			tmp = ImageIO.read(url);

		} catch (IOException ex) {

		}
		return tmp;
	}

	public  List<List<SettingsPicture>> getSettingsPictures(int rows,int cols)  {
		List<List<SettingsPicture>> settingsPictures=null;
		List<PictureData> picd=null;
		if(rows*cols==100){
		picd=getPictureData(rows,cols) ;
		settingsPictures =new ArrayList<List<SettingsPicture>>();

		
			PictureData pic=null;
			String id="",url;
			 int s=0;
		   for (int r=0;r<100/cols;r++){
			   List<SettingsPicture> tmp= new ArrayList<SettingsPicture>();
			   for (int c=0;c<cols;c++){
				  
				   pic=picd.get(s);
				   url=pic.getUrlThumb();
				   id=pic.getId();
				   tmp.add(new SettingsPicture(id,this.getBufImage(url))); 
				   s++;
			   }
			   settingsPictures.add(tmp);
				
			}
	}
		return settingsPictures;
	}
	public List<PictureData>getPictureData(int row, int col){
		List<PictureData> picd = new ArrayList<PictureData>();
		for(int r=0;r<100;r++){
		    
			picd.add(new PictureData("idnum"+r,"/resource/img/"+r%10+".png","/resource/img/"+r%10+".png",12345,false));
		}
		return picd;
		
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


