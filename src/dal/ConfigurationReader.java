package dal;

/**
 * Klasse lånt av Chris Håland
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {
	private Properties ini;
	private FileInputStream stream;

	public ConfigurationReader(String filePath) {
		try {
			ini = new Properties();
			stream = new FileInputStream(filePath);

			ini.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}

	public String read(String key)  {
		return ini.getProperty(key);
	}
	
	public static void main(String[] args) {
		ConfigurationReader reader = new ConfigurationReader("config.ini");
		System.out.println(reader.read("consumer_key"));
		System.out.println(reader.read("consumer_secret"));
		System.out.println(reader.read("access_token"));
		System.out.println(reader.read("access_token_secret"));
	}
}