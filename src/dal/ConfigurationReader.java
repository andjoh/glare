package dal;

/**
 * Klasse lånt av Chris Håland
 * Klassen leser fra en fil og returnerer innholdet.
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {
	private Properties ini;
	private FileInputStream stream;

	public String read(String key) {
		return ini.getProperty(key);
	}

	public void setPath(String filePath) {
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
}