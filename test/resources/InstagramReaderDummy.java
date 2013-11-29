package resources;

import java.util.*;

import dal.*;

public class InstagramReaderDummy implements IReader{
	public List<PictureData> getPictures(String searchTag) {

		List<PictureData> pictureData = new ArrayList<PictureData>();
		
		PictureData p = new PictureData();
		p.setId("picID 1");
		p.setCreatedTime(1);
		p.setUrlStd("http://distilleryimage3.s3.amazonaws.com/ec234b644b9c11e39c6812ce5dbdd1cb_8.jpg");
		p.setUrlThumb("http://distilleryimage3.s3.amazonaws.com/ef1236282adb11e3a9b322000a9e5afc_5.jpg");
		p.addHashtag(new Hashtag(searchTag));
		pictureData.add(p);
		
		p = new PictureData();
		p.setId("picID 2");
		p.setCreatedTime(2);
		p.setUrlStd("http://distilleryimage3.s3.amazonaws.com/f2275bfc4a1d11e39a131216ecc38082_8.jpg");
		p.setUrlThumb("http://images.ak.instagram.com/profiles/profile_186344368_75sq_1380238572.jpg");
		p.addHashtag(new Hashtag(searchTag));
		pictureData.add(p);
		
		p = new PictureData();
		p.setId("picID 4");
		p.setCreatedTime(4);
		p.setUrlStd("http://distilleryimage4.s3.amazonaws.com/95583912472711e391f8122b2cbdf00c_8.jpg");
		p.setUrlThumb("http://images.ak.instagram.com/profiles/profile_186344368_75sq_1380238572.jpg");
		p.addHashtag(new Hashtag(searchTag));
		pictureData.add(p);

		p = new PictureData();
		p.setId("picID 2");
		p.setCreatedTime(2);
		p.setUrlStd("http://distilleryimage3.s3.amazonaws.com/f2275bfc4a1d11e39a131216ecc38082_8.jpg");
		p.setUrlThumb("http://images.ak.instagram.com/profiles/profile_186344368_75sq_1380238572.jpg");
		p.addHashtag(new Hashtag(searchTag));
		pictureData.add(p);
		
		return pictureData;
	}
}