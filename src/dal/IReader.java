package dal;

import java.util.List;

public interface IReader {
	
	public List<PictureData> getPictures(String searchTag) throws Exception;
	
}
