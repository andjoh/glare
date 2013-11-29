package bll;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import net.coobird.thumbnailator.Thumbnails;


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
	public ImageIcon getIcon(int w, int h) {
		try {
			BufferedImage img=Thumbnails.of(image).size(w, h)
			.asBufferedImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ImageIcon("img");
	}
	public ImageIcon getIconTest(){
		
		URL url = this.getClass().getResource("/resource/img/settings.gif");
		BufferedImage buf = null;
		try {
			buf = ImageIO.read(url);
			buf = Thumbnails.of(buf).size(50, 50)
					.asBufferedImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ImageIcon(buf);
		
		

	}
	public void setIsFlagged(boolean isFlagged){
		this.isFlagged=isFlagged;
	}
	public boolean getIsFlagged(){
		return isFlagged;
	}
}