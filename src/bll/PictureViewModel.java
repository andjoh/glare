package bll;

import java.awt.image.BufferedImage;

public class PictureViewModel {
	private BufferedImage picture;     // Not sure if this is correct type?
	private String id;


	public PictureViewModel(BufferedImage picture, String id) {
		this.picture = picture;
		this.id      = id;
	}
	
	
	public BufferedImage getPicture() {
		return picture;
	}


	public String getId() {
		return id;
	}
}