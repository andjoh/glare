package bll;

import java.awt.image.BufferedImage;

public class SettingsPicture {
	private String id;
	private boolean isFlagged=false;
	private BufferedImage image;
	
	public SettingsPicture(String id, BufferedImage image) {
		this.id    = id;
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public BufferedImage getImage() {
		return image;
	}
	public void setIsFlagged(boolean isFlagged){
		this.isFlagged=isFlagged;
	}
	public boolean getIsFlagged(){
		return isFlagged;
	}
}