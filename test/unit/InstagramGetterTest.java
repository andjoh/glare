package unit;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import glare.ClassFactory;

import java.io.IOException;
import java.util.List;


import org.junit.Test;

import dal.InstagramReader;
import dal.PictureData;


public class InstagramGetterTest {

	@Test
	public void GetPictures_WhenCalled_ReturnsListOfPictures() throws IOException {
		InstagramReader getter = (InstagramReader) ClassFactory.getBeanByName("instagramReader");
		List<PictureData> pictures = getter.getPictures("winter");
		for(PictureData pic1: pictures){
			System.out.println(pic1.getId() + " " + pic1.getUrlStd() + ": " + pic1.getUrlThumb() + " "
					+ pic1.getCreatedTime() + " " + pic1.getHashtag() + " " + pic1.isRemoveFlag());
		}
		assertThat(pictures.isEmpty(),is(false));
	}

}
