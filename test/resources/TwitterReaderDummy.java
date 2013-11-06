package resources;

import java.util.*;

import dal.IReader;
import dal.PictureData;

public class TwitterReaderDummy implements IReader{
	public List<PictureData> getPictures(String searchTag) {

		List<PictureData> pictureData = new ArrayList<PictureData>();

		PictureData p = new PictureData();
		p.setId("picID 1");
		p.setCreatedTime(1);
		p.setUrlStd("http://distilleryimage3.s3.amazonaws.com/ef1236282adb11e3a9b322000a9e5afc_6.jpg");
		p.setUrlThumb("http://distilleryimage3.s3.amazonaws.com/ef1236282adb11e3a9b322000a9e5afc_5.jpg");
		pictureData.add(p);
		
		p = new PictureData();
		p.setId("picID 7");
		p.setCreatedTime(7);
		p.setUrlStd("http://distilleryimage3.s3.amazonaws.com/ef1236282adb11e3a9b322000a9e5afc_8.jpg");
		p.setUrlThumb("http://images.ak.instagram.com/profiles/profile_186344368_75sq_1380238572.jpg");
		pictureData.add(p);
		
		p = new PictureData();
		p.setId("picID 8");
		p.setCreatedTime(8);
		p.setUrlStd("http://distilleryimage3.s3.amazonaws.com/ef1236282adb11e3a9b322000a9e5afc_8.jpg");
		p.setUrlThumb("http://images.ak.instagram.com/profiles/profile_186344368_75sq_1380238572.jpg");
		pictureData.add(p);

		p = new PictureData();
		p.setId("picID 7");
		p.setCreatedTime(7);
		p.setUrlStd("http://distilleryimage3.s3.amazonaws.com/ef1236282adb11e3a9b322000a9e5afc_8.jpg");
		p.setUrlThumb("http://images.ak.instagram.com/profiles/profile_186344368_75sq_1380238572.jpg");
		pictureData.add(p);
		
		return pictureData;
	}
}