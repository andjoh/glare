package dal;

import java.util.List;

public interface IReader {
	/**
	 * Is used by TwitterReader & InstagramReader
	 * 
	 * @param searchTag
	 * @return
	 * @throws Exception
	 */
	public List<PictureData> getPictures(String searchTag) throws Exception;

}
