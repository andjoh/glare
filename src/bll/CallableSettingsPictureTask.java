package bll;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

public class CallableSettingsPictureTask implements Callable<SettingsPicture> {
	private String id, url;

	public CallableSettingsPictureTask(String id, String url) {
		this.id = id;
		this.url = url;

	}

	@Override
	public SettingsPicture call() throws Exception {
		// TODO Auto-generated method stub

		URL imageUrl;
		BufferedImage image = null;

		try {

			imageUrl = new URL(url);
			HttpURLConnection urlConn = (HttpURLConnection) imageUrl
					.openConnection();

			InputStream is = urlConn.getInputStream();
			image = ImageIO.read(is);
			is.close();

		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			System.out.println("IO EXEPTION !!!!!!! url: " + url);
		}
		if (image == null)
			return null;
		return new SettingsPicture(id, image);

	}

}
