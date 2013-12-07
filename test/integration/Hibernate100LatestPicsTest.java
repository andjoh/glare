package integration;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import dal.DatabaseHandler;
import dal.PictureData;
import static org.hamcrest.CoreMatchers.*;

public class Hibernate100LatestPicsTest {
	List<PictureData> picList;

	@Test
	public void ReturnHundredLatestPicturesFromDB() {
		picList = DatabaseHandler.listHundredNewestPicturesFromDB();
		
		for(PictureData pd : picList){
			System.out.println(pd.toString());
		}
		System.out.println(picList.size());
		assertThat(picList.size(), is(not(nullValue())));
	}

}
