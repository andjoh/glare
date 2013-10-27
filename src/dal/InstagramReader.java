package dal;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class InstagramReader implements IReader{

	InstagramParser parser;

	public InstagramReader(InstagramParser parser){
		this.parser = parser;
	}

	public List<PictureData> getPictures(String searchTag) {
		String instagramURL = "https://api.instagram.com/v1/tags/"+searchTag+"/media/recent?client_id=";

		URL url;
		HttpURLConnection connection = null;
		InputStreamReader reader = null;
		try {
			url = new URL(instagramURL);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			reader = new InputStreamReader(connection.getInputStream());
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

		List<PictureData> pictures = parser.parse(reader, searchTag);

		return pictures;
	}
}