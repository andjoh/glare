package unit;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.util.List;


import org.junit.Test;

import dal.InstagramParser;
import dal.InstagramReader;
import dal.PictureData;


public class InstagramGetterTest {

	@Test
	public void GetPictures_WhenCalled_ReturnsListOfPictures() throws IOException {
		InstagramReader getter = new InstagramReader(new InstagramParser());
		List<PictureData> pictures = getter.getPictures("winter");
		assertThat(pictures.isEmpty(),is(false));
	}

}
