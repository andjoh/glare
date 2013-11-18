package bll;

import java.awt.image.BufferedImage;

public class SettingsPicture {
	private String id;
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
}