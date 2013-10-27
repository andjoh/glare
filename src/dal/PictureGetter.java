package dal;

import java.util.List;

public class PictureGetter 
{
	private IReader reader;

	public void setReader(IReader reader)
	{
		this.reader = reader;
	}

	public List<PictureData> getPictures(String query)
	{

		try {
			return this.reader.getPictures(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}