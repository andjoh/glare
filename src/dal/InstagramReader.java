package dal;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class InstagramReader implements IReader{

	InstagramParser parser;
	ConfigurationReader confReader;

	public InstagramReader(InstagramParser parser, ConfigurationReader confReader){
		this.parser = parser;
		this.confReader = confReader;
	}

	public List<PictureData> getPictures(String searchTag) {
		confReader.setPath("src/resource/conf.ini");
		String instagramURL = "https://api.instagram.com/v1/tags/"+searchTag+"/media/recent?client_id=" + confReader.read("client_id");

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