package bll;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import net.coobird.thumbnailator.Thumbnails;


public class SettingsPicture {
	private String id;
	private boolean isFlagged,isTempFlagged;
	private BufferedImage image;
	
	public SettingsPicture(String id, BufferedImage image) {
		this.id    = id;
		this.image = image;
		 isFlagged=false;
		 isTempFlagged=false;
	}

	public String getId() {
		return id;
	}

	public BufferedImage getImage() {
		return image;
	}
	public ImageIcon getIcon(int w, int h) {
		BufferedImage img=null;
	
		
		try {if(image!=null)
      img=Thumbnails.of(image).size(w, h)
			.asBufferedImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    ImageIcon ic=null;
	    if(image!=null)ic= new ImageIcon(img);
		return ic;
	}
	public void setIsTempFlagged(boolean isTempFlagged){
		this.isTempFlagged=isTempFlagged;
	}
	public boolean getIsTempFlagged(){
		return isTempFlagged;
	}
	public void setIsFlagged(boolean isFlagged){
		this.isFlagged=isFlagged;
	}
	public boolean getIsFlagged(){
		return isFlagged;
	}
}