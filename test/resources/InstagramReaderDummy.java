package resources;

import java.util.*;
import dal.*;

public class InstagramReaderDummy implements IReader{
	public List<PictureData> getPictures(String searchTag) {

		List<PictureData> pictureData = new ArrayList<PictureData>();
		
		PictureData p = new PictureData();
		p.setId("picID 1");
		p.setCreatedTime(1);
		p.setUrlStd("http://distilleryimage5.s3.amazonaws.com/b50b6e1097bd11e2934722000a9f3cae_7.jpg");
		p.setUrlThumb("http://distilleryimage5.s3.amazonaws.com/b50b6e1097bd11e2934722000a9f3cae_7.jpg");
		p.addHashtag(new Hashtag(searchTag));
		pictureData.add(p);
		
		p = new PictureData();
		p.setId("picID 2");
		p.setCreatedTime(2);
		p.setUrlStd("http://distilleryimage5.s3.amazonaws.com/b50b6e1097bd11e2934722000a9f3cae_7.jpg");
		p.setUrlThumb("http://distilleryimage5.s3.amazonaws.com/b50b6e1097bd11e2934722000a9f3cae_7.jpg");
		p.addHashtag(new Hashtag(searchTag));
		pictureData.add(p);
		
		p = new PictureData();
		p.setId("picID 4");
		p.setCreatedTime(4);
		p.setUrlStd("http://distilleryimage5.s3.amazonaws.com/b50b6e1097bd11e2934722000a9f3cae_7.jpg");
		p.setUrlThumb("http://distilleryimage5.s3.amazonaws.com/b50b6e1097bd11e2934722000a9f3cae_7.jpg");
		p.addHashtag(new Hashtag(searchTag));
		pictureData.add(p);
		
		return pictureData;
	}
}